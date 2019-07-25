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

package de.d3adspace.echidna;

import de.d3adspace.echidna.config.EchidnaConfig;
import de.d3adspace.mantikor.commons.HTTPRequest;
import de.d3adspace.mantikor.commons.HTTPResponse;
import de.d3adspace.mantikor.commons.HTTPResponseBuilder;
import de.d3adspace.mantikor.server.MantikorServer;
import de.d3adspace.mantikor.server.config.MantikorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic Server implementation.
 *
 * @author Felix Klauke (info@felix-klauke.de)
 */
public class SimpleEchidnaServer extends MantikorServer implements EchidnaServer {

  /**
   * Logger for server actions.
   */
  private final Logger logger;

  /**
   * Config for the server.
   */
  private EchidnaConfig config;

  /**
   * Create a new Echidna server.
   *
   * @param config The server config.
   */
  public SimpleEchidnaServer(EchidnaConfig config) {
    super(MantikorConfig.builder()
        .serverHost(config.getServerHost())
        .serverPort(config.getServerPort())
        .build());
    this.logger = LoggerFactory.getLogger(SimpleEchidnaServer.class);
    this.config = config;
  }

  @Override
  public void start() {

    super.start();

    this.logger.info("Started the server on {}:{}.", this.config.getServerHost(),
        this.config.getServerPort());
  }

  @Override
  public void stop() {
    this.logger.info("Server is going to stop.");

    super.stop();

    this.logger.info("Server stopped.");
  }

  @Override
  protected HTTPResponse handleRequest(HTTPRequest httpRequest) {

    return new HTTPResponseBuilder()
        .createHTTPResponse();
  }

  @Override
  public boolean isRunning() {

    // TODO: Check if running
    return true;
  }
}
