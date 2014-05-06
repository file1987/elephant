package com.elephant.framework.network.http.tomcat;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.apache.catalina.startup.Tomcat;

import com.elephant.framework.network.http.IFHttpServer;

public class TomcatHttpServer implements IFHttpServer {
	
	private int port;
	private String path;
	
	
	
	@Override
	public void startUp() throws Exception {
		// TODO Auto-generated method stub
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.setBaseDir(path);

	}

	@Override
	public void shutdown() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void registerServlet(String name,Class<? extends Servlet> servletClass, String servletMapping) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerFilter(String name, Class<? extends Filter> filterClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerFilter(String name,Class<? extends Filter> filterClass, Map<String, String> initParams) {
		// TODO Auto-generated method stub

	}

}
