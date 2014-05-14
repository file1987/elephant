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
/**
 * 数据库工厂
 * @author file
 * @since 2014年5月14日 下午5:42:41
 * @version 1.0
 */
public class DatabaseFactory {
	
	private static final Logger logger = Logger.getLogger(DatabaseFactory.class);
	private boolean isInit = false;
	private String defaultClass = "org.logicalcobwebs.proxool.ProxoolDriver";
	
	private DatabaseFactory(){
		
	}
	/**
	 * 获取数据库操作协作类实例（单例）
	 * @return
	 */
	public static DatabaseFactory getInstance(){
		return DatabaseFactoryHolder.DATABASE_FACTORY;
	}
	/**
	 * 启动时必须调用该方法进行数据库连接池初始化
	 * @param xmlPath 配置文件的路径
	 * @return 是否成功初始化
	 */
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
	/**
	 * 是否已经初始化
	 * @return
	 */
	public boolean isInit(){
		return isInit;
	}
	/**
	 * 打印连接池信息
	 */
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
	private String defaultAliases = "default";
	/**
	 * 获取默认数据库连接
	 * @return
	 */
	public Connection getConnection(){
		return getConnection(defaultAliases);
	}
	/**
	 * 设置默认别名
	 * @param defaultAliases
	 */
	public void setDefaultAliases(String defaultAliases){
		this.defaultAliases = defaultAliases;
	}
	/**
	 * 获取数据库连接
	 * @param aliases 数据库别名
	 * @return
	 */
	public Connection getConnection(String aliases){
		try {
			return DriverManager.getConnection(aliases);
		} catch (SQLException e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	/**
	 * 关闭数据库连接
	 * @param conn
	 */
	public static void close(Connection conn){
		if(conn==null)
			throw new DatabaseException("数据库连接不能为null");
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	/**
	 * 数据库工厂协作类
	 * @author file
	 * @since 2014年5月14日 下午5:47:30
	 * @version 1.0
	 */
	private static class  DatabaseFactoryHolder{
		private static final DatabaseFactory DATABASE_FACTORY = new DatabaseFactory();
	}
	

}
