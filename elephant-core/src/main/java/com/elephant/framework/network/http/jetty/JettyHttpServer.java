package com.elephant.framework.network.http.jetty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.elephant.framework.network.http.IFHttpServer;

public class JettyHttpServer implements IFHttpServer {
	
	private int port;
	private String path;
	private Server server;
	private List<FServletInfo> servlets;
	private List<FilterHolder> filters;
	
	
	public JettyHttpServer() {
		this(8080);
	}
	
	

	public JettyHttpServer(int port) {
		this(port,null);
	}



	public JettyHttpServer(int port, String path) {
		this.port = port;
		this.path = path;
		servlets = new ArrayList<FServletInfo>();
		filters = new ArrayList<FilterHolder>();
	}



	@Override
	public void startUp() throws Exception {
		
		 server = new Server(port); 
		 ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		 context.setContextPath(path);
		 server.setHandler(context);
		 for(FServletInfo servlet:servlets)
		    context.addServlet(servlet.getServletHolder(), servlet.getMappingPath());
		 
		 for(FilterHolder filter:filters)
			 context.addFilter(filter, "/*", null);
		 
		 server.start();
		 server.join();
	}

	@Override
	public void shutdown() throws Exception {
		server.stop();
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
		servlets.add(new FServletInfo(new ServletHolder(name, servletClass), servletMapping));
	}

	@Override
	public void registerFilter(String name, Class<? extends Filter> filterClass) {
		FilterHolder filter = new FilterHolder(filterClass);
		filter.setName(name);
		filters.add(filter);

	}

	@Override
	public void registerFilter(String name,Class<? extends Filter> filterClass, Map<String, String> initParams) {
		FilterHolder filter = new FilterHolder(filterClass);
		filter.setName(name);
		filter.setInitParameters(initParams);
		filters.add(filter);
	}
	
	

}
