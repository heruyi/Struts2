package com.itheima.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * JNDI����Դ�Ĺ�����
 * @author zhy
 *
 */
public class JNDIUtil {

	//1.定义个数据源
	private static DataSource ds;
	
	//2.给数据源赋值
	static{
		try {
			Context initCtx = new InitialContext();
			ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/day28");
		} catch (NamingException e) {
			throw new ExceptionInInitializerError(e.toString());
		}
	}
	
	public static DataSource getDataSource(){
		return ds;
	}
	
	public static Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
