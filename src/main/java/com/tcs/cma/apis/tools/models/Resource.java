package com.tcs.cma.apis.tools.models;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author 44745
 *
 */
public class Resource implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4346753487150060977L;
	
	/**
	 * Path of the resource
	 */
	private String path;
	
	/**
	 * Verb name of the resource e.g. GET, POST, UPDATE, DELETE
	 */
	private String method;
	
	/**
	 * List of the parameters for the resource
	 */
	private List<ResourceParameter> parameters;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the parameters
	 */
	public List<ResourceParameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<ResourceParameter> parameters) {
		this.parameters = parameters;
	}
}
