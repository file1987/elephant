package com.elephant.framework.network.http;

import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.Servlet;

import com.elephant.framework.network.IFServer;
/**
 * http服务器接口
 * @author file
 * @since 2014年5月4日 下午8:19:15
 * @version 1.0
 */
public interface IFHttpServer extends IFServer {
	/**
	 * 获取路径
	 * @return
	 */
	public String getPath();
	/**
	 * 设置路径
	 * @param path
	 */
	public void setPath(final String path);
	/**
	 * 注册servlet
	 * @param name 名称
	 * @param servletClass servlet class
	 * @param servletMapping  路径映射
	 */
	public void registerServlet(final String name, final Class<? extends Servlet> servletClass,final String servletMapping);
	/**
	 * 注册Filter过滤器
	 * @param name 名称
	 * @param filterClass Filter class
	 */
	public void registerFilter(final String name, final Class<? extends Filter> filterClass);
	/**
	 * 注册Filter过滤器
	 * @param name 名称
	 * @param filterClass Filter class
	 * @param initParams  初始化参数
	 */
	public void registerFilter(final String name, final Class<? extends Filter> filterClass,Map<String, String> initParams);

}
