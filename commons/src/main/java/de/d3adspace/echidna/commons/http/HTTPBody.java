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

/**
 * Representing the body of a packet.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class HTTPBody {

  /**
   * Raw data of a body.
   */
  private final byte[] handle;

  /**
   * Create a body by its raw data.
   *
   * @param handle The data.
   */
  public HTTPBody(byte[] handle) {
    this.handle = handle;
  }

  /**
   * Create a bddy by its string.
   *
   * @param string The string.
   * @return The body.
   */
  public static HTTPBody fromString(String string) {
    return new HTTPBody(string.getBytes());
  }

  public static BodyBuilder newBodyBuilder(String initialName, String initialValue) {
    return new BodyBuilder(initialName, initialValue);
  }

  /**
   * Get the byte data of a body.
   */
  public byte[] getHandle() {
    return handle;
  }

  @Override
  public String toString() {
    return new String(this.handle);
  }

  public static class BodyBuilder {

    private StringBuilder stringBuilder = new StringBuilder();

    BodyBuilder(String name, String value) {
      stringBuilder.append(name).append("=").append(value);
    }

    public BodyBuilder form(String key, String value) {
      stringBuilder.append("&").append(key).append("=").append(value);
      return this;
    }

    public HTTPBody build() {
      return HTTPBody.fromString(this.stringBuilder.toString());
    }
  }
}
