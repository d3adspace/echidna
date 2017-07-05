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

package de.d3adspace.echidna.commons.http;

import java.util.Date;

/**
 * Representing a http response.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class HTTPResponse {
	
	/**
	 * The status.
	 */
	private HTTPStatus status;
	
	/**
	 * The headers.
	 */
	private HTTPHeaders headers;
	
	/**
	 * The body.
	 */
	private HTTPBody body;
	
	/**
	 * Create a response based on all its data.
	 *
	 * @param status The status.
	 * @param headers The headers.
	 * @param body The body.
	 */
	public HTTPResponse(HTTPStatus status, HTTPHeaders headers,
		HTTPBody body) {
		this.status = status;
		this.headers = headers;
		this.body = body;
	}
	
	/**
	 * Create an empty response.
	 */
	public HTTPResponse() {
		this.status = HTTPStatus.OK;
		this.headers = new HTTPHeaders();
	}
	
	/**
	 * Create a new builder.
	 *
	 * @return the builder.
	 */
	public static HTTPResponseBuilder newBuilder() {
		return new HTTPResponseBuilder();
	}
	
	/**
	 * Write the default headers.
	 */
	public void writeDefaultHeader() {
		headers.addHeader("Date", new Date().toString());
		headers.addHeader("Server", "Echidna Agent v1");
		
		if (body != null) {
			headers.addHeader("Content-Length", Integer.toString(body.getHandle().length));
		}
	}
	
	/**
	 * Get the status.
	 *
	 * @return The status.
	 */
	public HTTPStatus getStatus() {
		return status;
	}
	
	/**
	 * Get the body.
	 *
	 * @return The body.
	 */
	public HTTPBody getBody() {
		return body;
	}
	
	/**
	 * Get the headers.
	 *
	 * @return The headers.
	 */
	public HTTPHeaders getHeaders() {
		return headers;
	}
	
	@Override
	public String toString() {
		return "HTTPResponse{" +
			"status=" + status +
			", headers=" + headers +
			//", body=" + body +
			'}';
	}
}
