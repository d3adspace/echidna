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

package de.d3adspace.echidna.resource;

import de.d3adspace.echidna.annotation.Path;
import de.d3adspace.echidna.commons.http.HTTPRequest;
import de.d3adspace.echidna.config.EchidnaConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Manager for all available resources.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class ResourceManager {

  /**
   * The underlying Map.
   */
  private final Map<String, Resource> resources;

  /**
   * Create a Manager by a config.
   *
   * @param config The config.
   */
  public ResourceManager(EchidnaConfig config) {
    this.resources = new HashMap<>();

    config.getResourceClasses().forEach(this::addResource);
  }

  /**
   * Register a resource.
   *
   * @param resourceInstance The instance of the resource.
   */
  private void addResource(Object resourceInstance) {
    String rootPath = resourceInstance.getClass().getAnnotation(Path.class).value();
    Resource resource = new Resource(rootPath, resourceInstance);
    this.resources.put(rootPath, resource);
  }

  /**
   * Search for a resource by its rooting path.
   *
   * @param request The request.
   * @return The resource.
   */
  public Resource findResource(HTTPRequest request) {
    return this.resources.entrySet().stream()
        .filter(entry -> request.getLocation().startsWith(entry.getKey()))
        .map(Entry::getValue)
        .findFirst().orElse(null);
  }
}
