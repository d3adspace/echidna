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

import de.d3adspace.echidna.commons.http.HTTPMethod;
import de.d3adspace.echidna.commons.http.HTTPResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Representing one method that can handle a request.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class ResourceMethod {

	/**
	 * The path to listen for.
	 */
	private final String path;

	/**
	 * The http method to listen for.
	 */
	private final HTTPMethod method;

	/**
	 * Which content type the method can consume.
	 */
	private final String consumes;

	/**
	 * Which content types this method will throw out.
	 */
	private final String produces;

	/**
	 * The internal method to invoke.
	 */
	private final Method declaredMethod;

	/**
	 * The instance of the resource.
	 */
	private final Object resourceInstance;

	/**
	 * List of all possible post params.
	 */
	private final List<String> postParams = new ArrayList<>();

	/**
	 * Create a new method.
	 *
	 * @param path The path of the method.
	 * @param method The http method.
	 * @param consumes The consuming type.
	 * @param produces The producing type.
	 * @param declaredMethod The internal method.
	 * @param resourceInstance The resource.
	 */
	ResourceMethod(String path, HTTPMethod method,
		String consumes, String produces, Method declaredMethod, Object resourceInstance) {
		this.path = path;
		this.method = method;
		this.consumes = consumes;
		this.produces = produces;
		this.declaredMethod = declaredMethod;
		this.resourceInstance = resourceInstance;
	}

	/**
	 * Get the http method.
	 *
	 * @return The http method.
	 */
	public HTTPMethod getMethod() {
		return method;
	}

	/**
	 * Get the path of the method.
	 *
	 * @return The path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Get the internal method of the resource.
	 *
	 * @return The method.
	 */
	public Method getDeclaredMethod() {
		return declaredMethod;
	}

	/**
	 * Get the instance of the resource.
	 *
	 * @return The resource.
	 */
	public Object getResourceInstance() {
		return resourceInstance;
	}

	/**
	 * Get the type of the content the method consumes.
	 *
	 * @return The type.
	 */
	public String getConsumes() {
		return consumes;
	}

	/**
	 * Get the type of the content the method produces.
	 *
	 * @return The type.
	 */
	public String getProduces() {
		return produces;
	}

	/**
	 * Get the post params.
	 *
	 * @return The post params.
	 */
	public List<String> getPostParams() {
		return postParams;
	}

	public void addPostParam(String name) {
		this.postParams.add(name);
	}

	/**
	 * Invoke the internal method.
	 *
	 * @param params The params to call the method.
	 *
	 * @return The response.
	 */
	public HTTPResponse invoke(Object... params) {
		try {
			return (HTTPResponse) this.declaredMethod.invoke(this.resourceInstance, params);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String toString() {
		return "ResourceMethod{" +
			"path='" + path + '\'' +
			", method=" + method +
			", consumes='" + consumes + '\'' +
			", produces='" + produces + '\'' +
			", declaredMethod=" + declaredMethod +
			", resourceInstance=" + resourceInstance +
			'}';
	}
}
