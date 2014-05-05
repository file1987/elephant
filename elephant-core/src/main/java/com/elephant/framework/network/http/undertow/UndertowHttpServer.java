package com.elephant.framework.network.http.undertow;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ServletInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import com.elephant.framework.network.http.IFHttpServer;

public class UndertowHttpServer implements IFHttpServer {
	
	private int port;
	
	private String serverPath;
	
	private Undertow server;
	
	private List<ServletInfo> servlets;
	
	private List<FilterInfo> filters;
	
	public UndertowHttpServer(){
		this(9001);
	}
	public UndertowHttpServer(int port){
		this(port,null);
	}
	public UndertowHttpServer(int port,String path){
		this.serverPath = path;
		this.port = port;
		servlets = new ArrayList<ServletInfo>();
		filters = new ArrayList<FilterInfo>();
	}

	@Override
	public void startUp() throws Exception {
		DeploymentInfo servletBuilder = deployment()
                .setClassLoader(UndertowHttpServer.class.getClassLoader())
                .setContextPath(serverPath)
                .setDeploymentName("game.war")
                .addServlets(servlets)
                .addFilters(filters);

        DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        HttpHandler servletHandler = manager.start();
        PathHandler path = Handlers.path(Handlers.redirect(serverPath)).addPrefixPath(serverPath, servletHandler);
        server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(path)
                .build();
        server.start();

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
	
	
	public  void registerServlet(ServletInfo servletInfo){
		servlets.add(servletInfo);
	}
	
	public void registerFilter(FilterInfo filterInfo){
		filters.add(filterInfo);
	}
	
	@Override
	public void registerServlet(final String name, final Class<? extends Servlet> servletClass,final String servletMapping){
		registerServlet(Servlets.servlet(name,servletClass).addMapping(servletMapping));
	}
	
	@Override
	public void registerFilter(final String name, final Class<? extends Filter> filterClass){
		registerFilter(Servlets.filter(name, filterClass));
	}
	
	@Override
	public void registerFilter(final String name, final Class<? extends Filter> filterClass,Map<String, String> initParams){
		FilterInfo filterInfo = Servlets.filter(name, filterClass);
		for (Entry<String, String> entry:initParams.entrySet()) {
			filterInfo.addInitParam(entry.getKey(), entry.getValue());
		}
		registerFilter(filterInfo);
	}
	
	
	@Override
	public String getPath() {
		return serverPath;
	}

	@Override
	public void setPath(String path) {
		serverPath = path;
	}
}
