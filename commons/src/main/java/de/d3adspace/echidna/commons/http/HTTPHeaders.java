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
 * Representing a header of a packet.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class HTTPHeaders {

  /**
   * Underlying Map.
   */
  private Map<String, String> handle;

  /**
   * Create headers by its data.
   *
   * @param handle The data.
   */
  public HTTPHeaders(Map<String, String> handle) {
    this.handle = handle;
  }

  /**
   * Create empty header data.
   */
  public HTTPHeaders() {
    this(new HashMap<>());
  }

  /**
   * Get the underlying data.
   *
   * @return The underlying data.
   */
  public Map<String, String> getHandle() {
    return handle;
  }

  /**
   * Add a header property.
   *
   * @param name The name.
   * @param value The value.
   */
  public void addHeader(String name, String value) {
    this.handle.put(name, value);
  }

  /**
   * Get the value of a property.
   *
   * @param name The name.
   * @return The value.
   */
  public String getHeader(String name) {
    return this.handle.get(name);
  }

  @Override
  public String toString() {
    return "HTTPHeaders{" +
        "handle=" + handle +
        '}';
  }
}
