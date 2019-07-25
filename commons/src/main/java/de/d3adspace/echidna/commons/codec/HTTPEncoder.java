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

package de.d3adspace.echidna.commons.codec;

import de.d3adspace.echidna.commons.http.HTTPResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import java.util.Map.Entry;

/**
 * A simple encoder for netty to handle a HTTPResponse
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class HTTPEncoder extends MessageToByteEncoder<HTTPResponse> {

	/**
	 * The version of the http protocol.
	 */
	private static final String HTTP_VERSION = "HTTP/1.1";

	/**
	 * Byte codes that indicates a new line.
	 */
	private static final byte[] NEW_LINE = "\r\n".getBytes();

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, HTTPResponse response,
		ByteBuf byteBuf) throws Exception {
		System.out.println("Response: " + response);

		String statusResponse = HTTP_VERSION + " "
			+ response.getStatus().getCode() + " "
			+ response.getStatus().getDescription() + new String(NEW_LINE);

		byteBuf.writeBytes(statusResponse.getBytes(CharsetUtil.UTF_8));

		for (Entry<String, String> entry : response.getHeaders().getHandle().entrySet()) {
			String line = entry.getKey() + ": " + entry.getValue();
			byteBuf.writeBytes(line.getBytes(CharsetUtil.UTF_8));
			byteBuf.writeBytes(NEW_LINE);
		}

		byteBuf.writeBytes(NEW_LINE);

		byteBuf.writeBytes(response.getBody().getHandle());
	}
}
