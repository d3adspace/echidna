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

import de.d3adspace.echidna.annotation.Consumes;
import de.d3adspace.echidna.annotation.DELETE;
import de.d3adspace.echidna.annotation.PATCH;
import de.d3adspace.echidna.annotation.POST;
import de.d3adspace.echidna.annotation.PUT;
import de.d3adspace.echidna.annotation.Path;
import de.d3adspace.echidna.annotation.PostKey;
import de.d3adspace.echidna.annotation.Produces;
import de.d3adspace.echidna.commons.http.HTTPMethod;
import de.d3adspace.echidna.commons.http.HTTPRequest;
import de.d3adspace.echidna.commons.http.HTTPResponse;
import de.d3adspace.echidna.util.StringUtils;
import de.d3adspace.echidna.util.URLTokenizer;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Representing a resource of the server.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class Resource {

	/**
	 * The rooting path of the resource.
	 */
	private final String rootPath;

	/**
	 * The Map for all Methods.
	 */
	private final Map<String, ResourceMethod> resourceMethods;

	/**
	 * Create a new resource based on its rooting path and the instance.
	 *
	 * @param rootPath The path.
	 * @param resourceInstance The instance.
	 */
	Resource(String rootPath, Object resourceInstance) {
		this.rootPath = rootPath;
		this.resourceMethods = new HashMap<>();

		this.findResourceMethods(resourceInstance);
	}

	/**
	 * Find all handler mehtods of the resource.
	 *
	 * @param resourceInstance The instance.
	 */
	private void findResourceMethods(Object resourceInstance) {
		for (Method declaredMethod : resourceInstance.getClass().getDeclaredMethods()) {
			Path methodPath = declaredMethod.getAnnotation(Path.class);

			if (methodPath == null) {
				continue;
			}

			Consumes consumes = declaredMethod.getAnnotation(Consumes.class);
			Produces produces = declaredMethod.getAnnotation(Produces.class);

			HTTPMethod method = HTTPMethod.GET;

			if (declaredMethod.isAnnotationPresent(POST.class)) {
				method = HTTPMethod.POST;
			} else if (declaredMethod.isAnnotationPresent(DELETE.class)) {
				method = HTTPMethod.DELETE;
			} else if (declaredMethod.isAnnotationPresent(PUT.class)) {
				method = HTTPMethod.PUT;
			} else if (declaredMethod.isAnnotationPresent(PATCH.class)) {
				method = HTTPMethod.PATCH;
			}

			ResourceMethod resourceMethod = new ResourceMethod(methodPath.value(), method,
				consumes.value(), produces.value(), declaredMethod, resourceInstance);

			for (Parameter parameter : declaredMethod.getParameters()) {
				if (parameter.isAnnotationPresent(PostKey.class)) {
					resourceMethod.addPostParam(parameter.getAnnotation(PostKey.class).value());
				}
			}

			this.resourceMethods.put(methodPath.value(), resourceMethod);
		}
	}

	/**
	 * Get the rooting path of the resource.
	 *
	 * @return The path.
	 */
	public String getRootPath() {
		return rootPath;
	}

	/**
	 * Handle a request.
	 *
	 * @param httpRequest The http request.
	 *
	 * @return The response.
	 */
	public HTTPResponse handleRequest(HTTPRequest httpRequest) {
		for (Entry<String, ResourceMethod> entry : this.resourceMethods.entrySet()) {
			ResourceMethod resourceMethod = entry.getValue();

			if (httpRequest.getMethod() == resourceMethod.getMethod()
				&& Arrays.asList(httpRequest.getHeaders().getHeader("Accept").split(","))
				.contains(resourceMethod.getConsumes())) {

				List<Object> parameters = new ArrayList<>();
				parameters.add(httpRequest);

				if (httpRequest.getMethod() == HTTPMethod.POST) {
					for (String parameter : resourceMethod.getPostParams()) {
						parameters.add(httpRequest.getPostData().get(parameter));
					}
				}

				int requestSlashes = StringUtils.countMatches(httpRequest.getLocation(), "/");
				int methodSlashes =
					StringUtils.countMatches(resourceMethod.getPath(), "/") + StringUtils
						.countMatches(rootPath, "/");

				if (requestSlashes != methodSlashes) {
					continue;
				}

				if (resourceMethod.getPath().contains("{")) {
					URLTokenizer requestTokenizer = new URLTokenizer(
						httpRequest.getLocation().replaceAll(rootPath, ""));
					URLTokenizer methodTokenizer = new URLTokenizer(resourceMethod.getPath());

					if (requestTokenizer.countTokens() == methodTokenizer.countTokens()) {
						while (requestTokenizer.hasMoreTokens()) {
							String requestToken = requestTokenizer.nextToken();
							String methodToken = methodTokenizer.nextToken();

							if (methodToken.startsWith("{") && methodToken.endsWith("}")) {
								parameters.add(requestToken);
							}
						}
					}
				}

				return resourceMethod.invoke(parameters.toArray(new Object[parameters.size()]));
			}
		}

		return null;
	}
}
