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

package de.d3adspace.echidna.client;

import de.d3adspace.echidna.commons.http.HTTPBody;
import de.d3adspace.echidna.commons.http.HTTPHeaders;
import de.d3adspace.echidna.commons.http.HTTPRequest;
import de.d3adspace.echidna.commons.http.HTTPResponse;
import de.d3adspace.echidna.commons.http.HTTPStatus;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * TODO: Self explaining...
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class EchidnaClient {

	/**
	 * Local agent.
	 */
	private static final String AGENT = "Echidna Client Agent v1";

	/**
	 * Offset for codec.
	 */
	private static final int BYTE_OUT_OFFSET = 0;

	/**
	 * Perfrom a request to a server.
	 *
	 * @param url The url.
	 * @param request The request.
	 *
	 * @return The response.
	 */
	public HTTPResponse request(URL url, HTTPRequest request, HTTPBody body) {
		HttpURLConnection connection = this.createConnection(url, request, body);
		byte[] bytes = this.readByteArray(connection);

		try {
			Map<String, String> headers = connection.getHeaderFields().entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey,
					p -> p.getValue().stream().collect(Collectors.joining(";"))));

			return HTTPResponse.newBuilder()
				.setBody(new HTTPBody(bytes))
				.setStatus(HTTPStatus.getByCode(connection.getResponseCode()))
				.setHeaders(new HTTPHeaders(headers))
				.createHTTPResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Read the bytes by a connection.
	 *
	 * @param connection The connection.
	 *
	 * @return The bytes as array.
	 */
	private byte[] readByteArray(HttpURLConnection connection) {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			InputStream inputStream = connection.getInputStream()) {

			byte[] part = new byte[4096];

			int i;
			while ((i = inputStream.read(part)) > 0) {
				byteArrayOutputStream.write(part, BYTE_OUT_OFFSET, i);
			}

			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Open a new connection.
	 *
	 * @param url The url.
	 * @param request The request.
	 *
	 * @return The connection.
	 */
	private HttpURLConnection createConnection(URL url, HTTPRequest request, HTTPBody body) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5);
			connection.setRequestMethod(request.getMethod().name());
			connection.setRequestProperty("User-Agent", AGENT);
			connection.setDoOutput(true);

			if (body != null) {
				try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
					writer.write(body.getHandle());
				}
			}

			for (Map.Entry<String, String> entry : request.getHeaders().getHandle().entrySet()) {
				connection.setRequestProperty(entry.getKey(), entry.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return connection;
	}
}
