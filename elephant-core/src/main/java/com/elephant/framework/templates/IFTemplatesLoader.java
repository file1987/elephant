package com.elephant.framework.templates;

/**
 * 数据加载器（接口）
 * @author file
 * @since 2014年5月9日 下午5:22:24
 * @version 1.0
 * @param <T> 加载类型 : <code>keyValue类型，列表类型，多个列表（Map<name,列表>）类型</code>
 */
public interface IFTemplatesLoader<T> {
	/**
	 * 数据文件路径
	 * @return
	 */
	public String getFilePath();
	/**
	 * 数据内容的唯一key字段名称
	 * @return
	 */
	public String getUnionKey();
	/**
	 * 加载数据
	 * @return
	 */
	public  T  loaderData();
	/**
	 * 重新加载数据
	 * @param t
	 */
	public void reLoaderData(T t);
	/**
	 * 把数据转成lua文件 
	 * @param fileName  生成文件名
	 * @param var       数据的变量名称
	 * @throws Exception
	 */
	public void toLuaFile(String fileName,String var) throws Exception;
	/**
	 * 把数据转成json文件
	 * @param fileName  生成文件名
	 * @throws Exception 
	 */
	public void toJsonFile(String fileName) throws Exception;
	
	
}
