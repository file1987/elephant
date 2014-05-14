package com.elephant.framework.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import com.elephant.framework.exception.DatabaseException;
/**
 * baseDao的抽象父类
 * @author file
 * @since 2014年5月14日 下午5:37:56
 * @version 1.0
 * @param <O>
 */
public abstract class AbstractBaseDao<O> implements IFBaseDao<O> {

	@Override
	public void insert(O o) {
		execute(Oper.Insert,o);
	}
	/**
	 * insert sql
	 * @return
	 */
	protected abstract String insertSql();
	/**
	 * insert 操作所需的参数
	 * @param o
	 * @return
	 */
	protected abstract Map<Integer, ?> getInsertParams(O o);

	@Override
	public int update(O o) {
		return execute(Oper.Update,o);
	}
	/**
	 * update  sql
	 * @return
	 */
	protected abstract String updateSql();
	/**
	 * update操作所需的参数
	 * @param o
	 * @return
	 */
	protected abstract Map<Integer, ?> getUpdateParams(O o);

	@Override
	public int delete(O o) {
		return execute(Oper.Delete,o);
	}
	/**
	 * delete sql
	 * @return
	 */
	protected abstract String deleteSql() ;
	/**
	 * delete 操作所需的参数
	 * @param o
	 * @return
	 */
	protected abstract Map<Integer, ?> getDeleteParams(O o);

	@Override
	public void insertBatch(Collection<O> os) {
		execute(Oper.Insert,os);
	}

	@Override
	public int updateBatch(Collection<O> os) {
		return execute(Oper.Update,os);
	}

	@Override
	public int deleteBatch(Collection<O> os) {
		return execute(Oper.Delete,os);
	}

	@Override
	public Collection<O> getQueryResult(O o) {
		Map<Integer,?> params = getQueryParams(o);
		String sql = querySql();
		if(params==null||sql==null)
			throw new DatabaseException("Oper 类型不正确，或者对象转参数方法无实现");
		Connection conn = DatabaseFactory.getInstance().getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			for(Entry<Integer, ?> param:params.entrySet()){
				statement.setObject(param.getKey(), param.getValue());
			}
			return rs2Object(statement.executeQuery());
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}finally{			
			DatabaseFactory.close(conn);
		}
	}
	/**
	 * 数据库结果集转成对象集合
	 * @param rs 
	 * @return
	 * @throws SQLException
	 */
	protected abstract Collection<O> rs2Object(ResultSet rs) throws SQLException;
	/**
	 * 查询sql
	 * @return
	 */
	protected abstract String querySql();
	/**
	 * 查询操作所需的参数
	 * @param o
	 * @return
	 */
	protected abstract Map<Integer, ?> getQueryParams(O o) ;
	
	/**
	 * 执行操作
	 * @param conn
	 * @param oper
	 * @param o
	 * @return
	 */
	private int execute(Connection conn,Oper oper,O o){
		Map<Integer,?> params = null;
		String sql = null;
		switch (oper) {
		case Insert:
			params = getInsertParams(o);
			sql = insertSql();
			break;
		case Update:
			params = getUpdateParams(o);
			sql = updateSql();
			break;
		case Delete:
			params = getDeleteParams(o);
			sql = deleteSql();
			break;
		default:
			break;
		}
		if(params==null||sql==null)
			throw new DatabaseException("Oper 类型不正确，或者对象转参数方法无实现");
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			for(Entry<Integer, ?> param:params.entrySet()){
				statement.setObject(param.getKey(), param.getValue());
			}
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	/**
	 * 执行操作
	 * @param oper
	 * @param o
	 * @return
	 */
	private int execute(Oper oper,O o) {
		Connection conn = DatabaseFactory.getInstance().getConnection();
		int code = 0;
		try{
			code = execute(conn, oper,o);
		}finally{			
			DatabaseFactory.close(conn);
		}
		return code;
	}
	/**
	 * 执行批量操作
	 * @param oper
	 * @param o
	 * @return
	 */
	private int execute(Oper oper,Collection<O> o) {
		Connection conn = DatabaseFactory.getInstance().getConnection();
		int code = 0;
		try{
			code = execute(conn, oper,o);
		}finally{			
			DatabaseFactory.close(conn);
		}
		return code;
	}
	/**
	 * 执行批量操作
	 * @param conn
	 * @param oper
	 * @param os
	 * @return
	 */
	private int execute(Connection conn,Oper oper,Collection<O> os){
		Map<Integer,?> params = null;
		String sql = null;
		switch (oper) {
		case Insert:
			sql = insertSql();
			break;
		case Update:
			sql = updateSql();
			break;
		case Delete:
			sql = deleteSql();
			break;
		default:
			break;
		}
		if(sql==null)
			throw new DatabaseException("Oper 类型不正确，或者sql方法未实现");
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			
			for(O o:os){
				switch (oper) {
				case Insert:
					params = getInsertParams(o);
					break;
				case Update:
					params = getUpdateParams(o);
					break;
				case Delete:
					params = getDeleteParams(o);
					break;
				default:
					params=null;
					break;
				}
				if(params==null)
					continue;
				
				for(Entry<Integer, ?> param:params.entrySet()){
					statement.setObject(param.getKey(), param.getValue());
				}
				statement.addBatch();
			}
			int count = statement.executeBatch().length;
			conn.commit();
			conn.setAutoCommit(true);
			return count;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
	}
	
	
	
	@Override
	public void insert(Connection conn, O o) {
		execute(conn, Oper.Insert, o);
		
	}

	@Override
	public int update(Connection conn, O o) {
		return execute(conn, Oper.Update, o);
	}

	@Override
	public int delete(Connection conn, O o) {
		return execute(conn, Oper.Delete, o);
	}

	@Override
	public void insertBatch(Connection conn, Collection<O> os) {
		execute(conn, Oper.Insert, os);
	}

	@Override
	public int updateBatch(Connection conn, Collection<O> os) {
		return execute(conn, Oper.Update, os);
	}

	@Override
	public int deleteBatch(Connection conn, Collection<O> os) {
		return execute(conn, Oper.Delete, os);
	}
	/**
	 * 操作类型枚举
	 * @author file
	 * @since 2014年5月14日 下午5:42:27
	 * @version 1.0
	 */
	private enum Oper{
		Insert,
		Update,
		Delete,
		Query,
	}
	
	
}
