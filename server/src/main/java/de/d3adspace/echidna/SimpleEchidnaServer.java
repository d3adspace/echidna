/*
 * Copyright (c) 2017 D3adspace
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.d3adspace.echidna;

import de.d3adspace.echidna.commons.NettyUtils;
import de.d3adspace.echidna.commons.http.HTTPBody;
import de.d3adspace.echidna.commons.http.HTTPHeaders;
import de.d3adspace.echidna.commons.http.HTTPRequest;
import de.d3adspace.echidna.commons.http.HTTPResponse;
import de.d3adspace.echidna.commons.http.HTTPStatus;
import de.d3adspace.echidna.config.EchidnaConfig;
import de.d3adspace.echidna.initializer.ServerChannelInitializer;
import de.d3adspace.echidna.resource.Resource;
import de.d3adspace.echidna.resource.ResourceManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;

/**
 * Basic Server implementation.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class SimpleEchidnaServer implements EchidnaServer {
	
	/**
	 * Manager for all resources.
	 */
	private final ResourceManager resourceManager;
	
	/**
	 * Config for the server.
	 */
	private EchidnaConfig config;
	
	/**
	 * Netty boss group.
	 */
	private EventLoopGroup bossGroup;
	
	/**
	 * Netty worker group.
	 */
	private EventLoopGroup workerGroup;
	
	/**
	 * Channel to client.
	 */
	private Channel channel;
	
	/**
	 * Create a new Echidna server.
	 *
	 * @param config The server config.
	 */
	SimpleEchidnaServer(EchidnaConfig config) {
		this.resourceManager = new ResourceManager(config);
		this.config = config;
	}
	
	@Override
	public void start() {
		this.bossGroup = NettyUtils.createEventLoopGroup(1);
		this.workerGroup = NettyUtils.createEventLoopGroup(4);
		
		Class<? extends ServerChannel> serverChannelClass = NettyUtils.getServerChannelClass();
		
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		try {
			channel = serverBootstrap
				.group(bossGroup, workerGroup)
				.channel(serverChannelClass)
				.childHandler(new ServerChannelInitializer(this))
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.bind(config.getServerHost(), config.getServerPort())
				.sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		this.channel.close();
		
		this.bossGroup.shutdownGracefully();
		this.workerGroup.shutdownGracefully();
	}
	
	@Override
	public boolean isRunning() {
		return channel != null && channel.isActive();
	}
	
	/**
	 * Handling the given request.
	 *
	 * @param httpRequest the request.
	 *
	 * @return The response.
	 */
	public HTTPResponse handleRequest(HTTPRequest httpRequest) {
		Resource resource = this.resourceManager.findResource(httpRequest);
		HTTPResponse response = HTTPResponse.newBuilder()
			.setBody(new HTTPBody(new byte[0]))
			.setHeaders(new HTTPHeaders())
			.setStatus(HTTPStatus.NOT_FOUND)
			.createHTTPResponse();
		
		if (resource == null) {
			response.writeDefaultHeader();
			
			return response;
		}
		
		HTTPResponse newResponse = resource.handleRequest(httpRequest);
		response = newResponse == null ? response : newResponse;
		
		response.writeDefaultHeader();
		
		return response;
	}
}
