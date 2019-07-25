/*
 * Copyright (c) 2017 - 2019 D3adspace
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

package de.d3adspace.echidna.connection;

import de.d3adspace.echidna.SimpleEchidnaServer;
import de.d3adspace.echidna.commons.http.HTTPRequest;
import de.d3adspace.echidna.commons.http.HTTPResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Representing one connection to one client.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class EchidnaConnection extends SimpleChannelInboundHandler<HTTPRequest> {

	/**
	 * The channel to client.
	 */
	private final Channel channel;

	/**
	 * The underlying server.
	 */
	private final SimpleEchidnaServer server;

	/**
	 * Create a connection based on its channel and the server it belongs to.
	 *
	 * @param channel The channel.
	 * @param server The server.
	 */
	public EchidnaConnection(Channel channel, SimpleEchidnaServer server) {
		this.channel = channel;
		this.server = server;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext,
		HTTPRequest httpRequest) throws Exception {

		HTTPResponse response = this.server.handleRequest(httpRequest);

		this.channel.writeAndFlush(response);
	}
}
