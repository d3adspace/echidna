package de.d3adspace.echidna.server.resource;

public interface ResourceManager {

  /**
   * Register a resource by the given class.
   *
   * @param resourceClazz The resource class.
   */
  void registerResource(Class<?> resourceClazz);

  /**
   * Register a resource by its instance.
   *
   * @param resource The instance.
   */
  void registerResource(Object resource);
}
