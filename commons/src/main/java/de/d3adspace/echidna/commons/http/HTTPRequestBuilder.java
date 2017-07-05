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

/**
 * Builder for a request.
 */
public class HTTPRequestBuilder {
	
	/**
	 * The underlying raw data.
	 */
	private String rawRequestData;
	
	/**
	 * The http method.
	 */
	private HTTPMethod method;
	
	/**
	 * The location of the resource.
	 */
	private String location;
	
	/**
	 * The http version.
	 */
	private String version;
	
	/**
	 * The http headers.
	 */
	private HTTPHeaders headers;
	
	/**
	 * Set the raw data of a request.
	 *
	 * @param rawRequestData The raw data.
	 *
	 * @return The builder.
	 */
	public HTTPRequestBuilder setRawRequestData(String rawRequestData) {
		this.rawRequestData = rawRequestData;
		return this;
	}
	
	/**
	 * Set the http method.
	 *
	 * @param method The method.
	 *
	 * @return The builder.
	 */
	public HTTPRequestBuilder setMethod(HTTPMethod method) {
		this.method = method;
		return this;
	}
	
	/**
	 * Set the location of the resource.
	 *
	 * @param location The location.
	 *
	 * @return The builder.
	 */
	public HTTPRequestBuilder setLocation(String location) {
		this.location = location;
		return this;
	}
	
	/**
	 * Set the http version.
	 *
	 * @param version The version.
	 *
	 * @return The builder.
	 */
	public HTTPRequestBuilder setVersion(String version) {
		this.version = version;
		return this;
	}
	
	/**
	 * Set the http headers.
	 *
	 * @param headers The headers.
	 *
	 * @return The builder.
	 */
	public HTTPRequestBuilder setHeaders(HTTPHeaders headers) {
		this.headers = headers;
		return this;
	}
	
	/**
	 * Create the final config.
	 *
	 * @return The request.
	 */
	public HTTPRequest createHTTPRequest() {
		return new HTTPRequest(rawRequestData, method, location, version, headers);
	}
}