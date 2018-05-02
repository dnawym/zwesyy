package db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class comDAL {

	public ResultSet DoSelect(String sql) {
		ResultSet res = null;
		try {
			res = JdbcHelper.query(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public int execCommand(String sql) {
		int res = 0;
		try {
			res = JdbcHelper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public void close(){
		JdbcHelper.free();
	}
}
