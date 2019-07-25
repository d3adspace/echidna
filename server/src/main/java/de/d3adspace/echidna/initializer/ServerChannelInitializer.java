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

package de.d3adspace.echidna.initializer;

import de.d3adspace.echidna.SimpleEchidnaServer;
import de.d3adspace.echidna.commons.codec.HTTPDecoder;
import de.d3adspace.echidna.commons.codec.HTTPEncoder;
import de.d3adspace.echidna.connection.EchidnaConnection;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * The initializer for all the nice channels.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * The underlying server.
	 */
	private final SimpleEchidnaServer server;

	/**
	 * Create a new initializer.
	 *
	 * @param server The server.
	 */
	public ServerChannelInitializer(SimpleEchidnaServer server) {
		this.server = server;
	}

	/**
	 * The Method that will initialize the channel.
	 *
	 * @param socketChannel The channel.
	 *
	 * @throws Exception Codec exception.
	 */
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();

		pipeline.addLast(new HTTPDecoder());
		pipeline.addLast(new HTTPEncoder());
		pipeline.addLast(new EchidnaConnection(socketChannel, server));
	}
}
