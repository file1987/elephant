package com.elephant.framework.db;

import java.sql.Connection;
import java.util.Collection;
/**
 * 基础dao接口
 * @author file
 * @since 2014年5月14日 下午5:26:20
 * @version 1.0
 * @param <O>
 */
public interface IFBaseDao<O> {
	/**
	 * insert
	 * @param o
	 */
	public void insert(O o);
	/**
	 * update		
	 * @param o
	 * @return 更新条数
	 */
	public int update(O o);
	/**
	 * delete
	 * @param o
	 * @return 删除条数
	 */
	public int delete(O o);
	/**
	 * 批量insert
	 * @param os
	 */
	public void insertBatch(Collection<O> os);
	/**
	 * 批量update
	 * @param os
	 * @return 更新条数
	 */
	public int updateBatch(Collection<O> os);
	/**
	 * 批量delete
	 * @param os
	 * @return 删除条数
	 */
	public int deleteBatch(Collection<O> os);
	
	/**
	 * insert（需自己控制事务） 
	 * @param conn 数据库连接
	 * @param o
	 */
	public void insert(Connection conn,O o);
	/**
	 * update（需自己控制事务） 
	 * @param conn
	 * @param o
	 * @return  更新条数
	 */
	public int update(Connection conn,O o);
	/**
	 * delete（需自己控制事务） 
	 * @param conn
	 * @param o
	 * @return 删除条数
	 */
	public int delete(Connection conn,O o);
	/**
	 * 批量insert（需自己控制事务） 
	 * @param conn
	 * @param os
	 */
	public void insertBatch(Connection conn,Collection<O> os);
	/**
	 * 批量update（需自己控制事务） 
	 * @param conn
	 * @param os
	 * @return 更新数据条数
	 */
	public int updateBatch(Connection conn,Collection<O> os);
	/**
	 * 批量删除（需自己控制事务） 
	 * @param conn
	 * @param os
	 * @return 删除数据条数
	 */
	public int deleteBatch(Connection conn,Collection<O> os);
	
	/**
	 * 查询数据
	 * @param o
	 * @return 查询结果集
	 */
	public Collection<O> getQueryResult(O o);
		
}
