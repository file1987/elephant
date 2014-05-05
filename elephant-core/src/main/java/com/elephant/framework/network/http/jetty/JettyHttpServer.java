package com.elephant.framework.network.http.jetty;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;

import com.elephant.framework.network.http.IFHttpServer;

public class JettyHttpServer implements IFHttpServer {
	
	
	
	@Override
	public void startUp() throws Exception {
		
		 Server server = new Server(8080); 
		 server.start();
	}

	@Override
	public void shutdown() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPort(int port) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPath(String path) {
		// TODO Auto-generated method stub

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
	public void registerFilter(String name,
			Class<? extends Filter> filterClass, Map<String, String> initParams) {
		// TODO Auto-generated method stub

	}

}
