package com.elephant.framework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ConnectionPoolDefinitionIF;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import com.elephant.framework.exception.DatabaseException;

public class DatabaseFactory {
	
	private static final Logger logger = Logger.getLogger(DatabaseFactory.class);
	private boolean isInit = false;
	private String defaultClass = "org.logicalcobwebs.proxool.ProxoolDriver";
	
	private DatabaseFactory(){
		
	}
	
	public static DatabaseFactory getInstance(){
		return DatabaseFactoryHolder.DATABASE_FACTORY;
	}
	
	public boolean init(String xmlPath){
		if(isInit){
			logger.error("数据库连接池已初始化，无需重复初始化！");
			return false;
		}
			
		try {
			Class.forName(defaultClass);
			JAXPConfigurator.configure(xmlPath, false);
			isInit= true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (ProxoolException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return isInit;
	}
	
	public boolean isInit(){
		return isInit;
	}
	
	public void printProxoolInfo(){
		if(!isInit)
			return;
		StringBuilder builder = new StringBuilder();
		for(String aliases:ProxoolFacade.getAliases()){
			try {
				ConnectionPoolDefinitionIF connectionPoolDefinitionGameDb = ProxoolFacade.getConnectionPoolDefinition(aliases);
				builder.append(String.format("连接池别名=%s;数据库URL=%s;用户名=%s;\r\n", aliases, connectionPoolDefinitionGameDb.getUrl(), connectionPoolDefinitionGameDb.getUser()));
			} catch (ProxoolException e) {
				logger.error(e);
			}
		}
	}

	public Connection getConnection(){
		return getConnection("default");
	}
	
	public Connection getConnection(String aliases){
		try {
			return DriverManager.getConnection(aliases);
		} catch (SQLException e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public static void close(Connection conn){
		if(conn==null)
			throw new DatabaseException("数据库连接不能为null");
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	private static class  DatabaseFactoryHolder{
		private static final DatabaseFactory DATABASE_FACTORY = new DatabaseFactory();
	}
	

}
