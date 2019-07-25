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

import de.d3adspace.echidna.EchidnaServer;
import de.d3adspace.echidna.EchidnaServerFactory;
import de.d3adspace.echidna.annotation.Consumes;
import de.d3adspace.echidna.annotation.GET;
import de.d3adspace.echidna.annotation.POST;
import de.d3adspace.echidna.annotation.Path;
import de.d3adspace.echidna.annotation.PostKey;
import de.d3adspace.echidna.annotation.Produces;
import de.d3adspace.echidna.client.EchidnaClient;
import de.d3adspace.echidna.commons.http.HTTPBody;
import de.d3adspace.echidna.commons.http.HTTPHeaders;
import de.d3adspace.echidna.commons.http.HTTPMethod;
import de.d3adspace.echidna.commons.http.HTTPRequest;
import de.d3adspace.echidna.commons.http.HTTPResponse;
import de.d3adspace.echidna.commons.http.HTTPStatus;
import de.d3adspace.echidna.config.EchidnaConfig;
import de.d3adspace.echidna.config.EchidnaConfigBuilder;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class EchidnaServerExample {

	public static void main(String[] args) {
		EchidnaConfig config = new EchidnaConfigBuilder()
			.setServerPort(80)
			.setServerHost("localhost")
			.setResourceClasses(Collections.singletonList(new ExampleResource()))
			.createEchidnaConfig();

		EchidnaServer echidnaServer = EchidnaServerFactory.createEchidnaServer(config);

		echidnaServer.start();

		EchidnaClient client = new EchidnaClient();
		HTTPRequest request = HTTPRequest
			.newBuilder()
			.setLocation("rest/v1/user/add")
			.setMethod(HTTPMethod.POST)
			.setVersion("HTTP/1.1")
			.setHeaders(new HTTPHeaders())
			.createHTTPRequest();

		HTTPBody body = HTTPBody.newBodyBuilder("test", "IchBinDerBeste").build();

		HTTPResponse response = null;
		try {
			response = client.request(new URL("http://localhost/rest/v1/user/add"), request, body);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		System.out.println("Got Response: " + response);
	}

	@Path("/rest/v1")
	public static class ExampleResource {

		@GET
		@Path("/user/del")
		@Consumes("text/html")
		@Produces("text/plain")
		public HTTPResponse onUserDel(HTTPRequest request) {
			return HTTPResponse.newBuilder()
				.setStatus(HTTPStatus.OK)
				.setBody(HTTPBody.fromString("Hey"))
				.setHeaders(new HTTPHeaders())
				.createHTTPResponse();
		}

		@GET
		@Path("/user/del/{userId}")
		@Consumes("text/html")
		@Produces("text/plain")
		public HTTPResponse onUserDelId(HTTPRequest request, String userId) {
			return HTTPResponse.newBuilder()
				.setStatus(HTTPStatus.OK)
				.setBody(HTTPBody.fromString("Hey du " + userId))
				.setHeaders(new HTTPHeaders())
				.createHTTPResponse();
		}

		@POST
		@Path("/user/add")
		@Consumes("text/html")
		@Produces("text/plain")
		public HTTPResponse onUserDelIdPost(HTTPRequest request, @PostKey("test") String userId) {
			System.out.println("Parameter: " + userId);

			return HTTPResponse.newBuilder()
				.setStatus(HTTPStatus.OK)
				.setBody(HTTPBody.fromString("Hey du " + userId))
				.setHeaders(new HTTPHeaders())
				.createHTTPResponse();
		}
	}
}
