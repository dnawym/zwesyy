package db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcHelper {

	private static Connection conn = null;
	private static PreparedStatement preparedStatement = null;
	private static CallableStatement callableStatement = null;

	/**
	 * 用于查询，返回结果集
	 * 
	 * @param sql
	 *            sql语句
	 * @return 结果集
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	public static ResultSet query(String sql) throws SQLException {

		ResultSet rs = null;
		try {
			getPreparedStatement(sql);
			rs = preparedStatement.executeQuery();
			if (!rs.next()) {
				return null;
			}
			return rs;
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			//free();
		}

	}

	/**
	 * 返回单个结果的值，如count\min\max等等
	 * 
	 * @param sql
	 *            sql语句
	 * @return 结果集
	 * @throws SQLException
	 */
	public static Object getSingle(String sql) throws SQLException {
		Object result = null;
		ResultSet rs = null;
		try {
			getPreparedStatement(sql);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				result = rs.getObject(1);
			}
			return result;
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			//free();
		}
	}

	/**
	 * 用于增删改
	 * 
	 * @param sql
	 *            sql语句
	 * @return 影响行数
	 * @throws SQLException
	 */
	public static int update(String sql) throws SQLException {

		try {
			getPreparedStatement(sql);

			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			free();
		}
	}

	/**
	 * 获取PreparedStatement
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	private static void getPreparedStatement(String sql) throws SQLException {
		conn = JdbcUnits.getConnection();
		preparedStatement = conn.prepareStatement(sql);
	}

	/**
	 * 获取CallableStatement
	 * 
	 * @param procedureSql
	 * @throws SQLException
	 */
	private static void getCallableStatement(String procedureSql) throws SQLException {
		conn = JdbcUnits.getConnection();
		callableStatement = conn.prepareCall(procedureSql);
	}

	/**
	 * 释放资源
	 * 
	 * @param rs
	 *            结果集
	 */
	public static void free() {

		JdbcUnits.free(conn, preparedStatement);
	}

	/**
	 * 释放资源
	 * 
	 * @param statement
	 * @param rs
	 */
	public static void free(Statement statement) {
		JdbcUnits.free(conn, statement);
	}

}
