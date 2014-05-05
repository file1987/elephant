package com.elephant.framework.network;
/**
 *  服务器接口
 * @author file
 * @since 2014年4月26日 下午8:11:54
 * @version 1.0
 */
public interface IFServer {
	/**
	 * 服务器启动
	 * @throws Exception
	 */
	public  void  startUp() throws Exception ;
	/**
	 * 停止服务器
	 * @throws Exception
	 */
	public  void  shutdown() throws Exception;
	/**
	 * 服务器端口
	 * @return
	 */
	public int  getPort();
	/**
	 * 设置服务器端口
	 * @param port
	 */
	public void  setPort(int port);
	
	
	
}
