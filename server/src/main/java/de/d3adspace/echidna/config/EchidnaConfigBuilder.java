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

package de.d3adspace.echidna.config;

import java.util.List;

/**
 * Builder for a server config.
 */
public class EchidnaConfigBuilder {

  /**
   * The server host.
   */
  private String serverHost;

  /**
   * The server port.
   */
  private int serverPort;

  /**
   * The resources to handle.
   */
  private List<Object> resources;

  /**
   * Set the server host.
   *
   * @param serverHost The server host.
   * @return The builder.
   */
  public EchidnaConfigBuilder setServerHost(String serverHost) {
    this.serverHost = serverHost;
    return this;
  }

  /**
   * Set the server port.
   *
   * @param serverPort The server port.
   * @return The builder.
   */
  public EchidnaConfigBuilder setServerPort(int serverPort) {
    this.serverPort = serverPort;
    return this;
  }

  /**
   * Set the resources to handle.
   *
   * @param resourceClasses The resources.
   * @return The builder.
   */
  public EchidnaConfigBuilder setResourceClasses(List<Object> resourceClasses) {
    this.resources = resourceClasses;
    return this;
  }

  /**
   * Create the resulting config.
   *
   * @return The config.
   */
  public EchidnaConfig createEchidnaConfig() {
    return new EchidnaConfig(serverHost, serverPort, resources);
  }
}
