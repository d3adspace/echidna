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

package de.d3adspace.echidna.commons.http.parser;

import de.d3adspace.echidna.commons.http.HTTPHeaders;
import de.d3adspace.echidna.commons.http.HTTPMethod;
import de.d3adspace.echidna.commons.http.HTTPRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Parser for a HTTPRequest
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class HTTPRequestParser {

  /**
   * Parse a Request by its raw data.
   *
   * @param data The data.
   * @return The request.
   */
  public static HTTPRequest parseRequest(String data) {
    StringTokenizer tokenizer = new StringTokenizer(data);

    HTTPMethod method = HTTPMethod.valueOf(tokenizer.nextToken());
    String locationToken = tokenizer.nextToken();
    String location =
        locationToken.endsWith("/") ? locationToken.substring(0, locationToken.lastIndexOf('/'))
            : locationToken;
    String version = tokenizer.nextToken();
    HTTPHeaders headers = new HTTPHeaders(parseHeaders(data));

    HTTPRequest request = HTTPRequest.newBuilder()
        .setRawRequestData(data)
        .setMethod(method)
        .setLocation(location)
        .setVersion(version)
        .setHeaders(headers)
        .createHTTPRequest();

    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();

      if ("Content-Length:".equalsIgnoreCase(token)) {
        int length = Integer.parseInt(tokenizer.nextToken());

        if (length > 0) {
          request.parsePostData(tokenizer.nextToken());
        }
      }
    }

    return request;
  }

  /**
   * Parse the headers of a request.
   *
   * @param data The data.
   * @return The headers.
   */
  private static Map<String, String> parseHeaders(String data) {
    Map<String, String> headers = new HashMap<>();
    String[] lines = data.split("\\n");

    for (int i = 1; i < lines.length; i++) {
      String line = lines[i];
      String[] splitted = line.split(":", 2);

      if (splitted.length != 2) {
        continue;
      }

      headers.put(splitted[0].trim(), splitted[1].trim());
    }

    return headers;
  }
}
