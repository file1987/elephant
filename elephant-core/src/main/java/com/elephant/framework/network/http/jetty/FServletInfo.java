package com.elephant.framework.network.http.jetty;

import org.eclipse.jetty.servlet.ServletHolder;

public class FServletInfo {
	
	private ServletHolder servletHolder;
	private String mappingPath;
	
	public FServletInfo(ServletHolder servletHolder, String path) {
		super();
		this.servletHolder = servletHolder;
		this.mappingPath = path;
	}
	public ServletHolder getServletHolder() {
		return servletHolder;
	}
	public String getMappingPath() {
		return mappingPath;
	}
	
}
