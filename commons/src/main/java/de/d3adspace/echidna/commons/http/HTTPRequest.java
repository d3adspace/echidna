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

package de.d3adspace.echidna.commons.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Representing a http request.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class HTTPRequest {

	private static final String POST_DELIMETER = "&";

	/**
	 * The underlying raw data.
	 */
	private final String rawRequestData;

	/**
	 * The method of the request.
	 */
	private final HTTPMethod method;

	/**
	 * Location of the resource.
	 */
	private final String location;

	/**
	 * The http version.
	 */
	private final String version;

	/**
	 * The http headers.
	 */
	private final HTTPHeaders headers;

	/**
	 * Post data.
	 */
	private final Map<String, String> postData = new HashMap<>();

	/**
	 * Create a request based on all its data.
	 *
	 * @param rawRequestData The raw data.
	 * @param method The method.
	 * @param location The location.
	 * @param version The version.
	 * @param headers The headers.
	 */
	public HTTPRequest(String rawRequestData, HTTPMethod method, String location,
		String version, HTTPHeaders headers) {
		this.rawRequestData = rawRequestData;
		this.method = method;
		this.location = location;
		this.version = version;
		this.headers = headers;
	}

	/**
	 * Create a new builder.
	 *
	 * @return The builder.
	 */
	public static HTTPRequestBuilder newBuilder() {
		return new HTTPRequestBuilder();
	}

	/**
	 * Get the headers.
	 *
	 * @return The headers.
	 */
	public HTTPHeaders getHeaders() {
		return headers;
	}

	/**
	 * Get the method.
	 *
	 * @return The method.
	 */
	public HTTPMethod getMethod() {
		return method;
	}

	/**
	 * Location of the resource.
	 *
	 * @return The location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Get the underlying raw data.
	 *
	 * @return The raw data.
	 */
	public String getRawRequestData() {
		return rawRequestData;
	}

	/**
	 * Get the http version.
	 *
	 * @return The version.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Parse the post data of the request.
	 *
	 * @param postParameter The pastparam.
	 */
	public void parsePostData(String postParameter) {
		String[] split = postParameter.split(POST_DELIMETER);

		for (String splitElement : split) {
			String[] params = splitElement.split("=");
			if (params.length == 2) {
				postData.put(params[0], params[1]);
			}
		}
	}

	/**
	 * Get all the post data.
	 *
	 * @return The data.
	 */
	public Map<String, String> getPostData() {
		return postData;
	}

	@Override
	public String toString() {
		return "HTTPRequest{" +
			//"rawRequestData='" + rawRequestData + '\'' +
			", method=" + method +
			", location='" + location + '\'' +
			", version='" + version + '\'' +
			", headers=" + headers +
			", postData=" + postData +
			'}';
	}
}
