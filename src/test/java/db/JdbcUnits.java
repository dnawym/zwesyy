package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUnits {
	/**
	 * 数据库连接地址
	 */
	private static String url = "jdbc:mysql://localhost:3306/world?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	/**
	 * 用户名
	 */
	private static String userName = "root";
	/**
	 * 密码
	 */
	private static String password = "root";

	private static String driver = "com.mysql.jdbc.Driver";

	/**
	 * 装载驱动
	 */
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * 建立数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(url, userName, password);
		return conn;
	}

	/**
	 * 释放连接
	 * 
	 * @param conn
	 */
	private static void freeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放statement
	 * 
	 * @param statement
	 */
	private static void freeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放resultset
	 * 
	 * @param rs
	 */
	private static void freeResultSet(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param statement
	 * @param rs
	 */
	public static void free(Connection conn, Statement statement) {
		if (statement != null) {
			freeStatement(statement);
		}
		if (conn != null) {
			freeConnection(conn);
		}
	}

}
