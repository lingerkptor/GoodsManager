package idv.GoodsManager.installation.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class QueryDataSQL implements PreparedStatementCreator {

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) {
		String SQL = "select * from test ;";
		PreparedStatement preps = null;
		try {
			preps = conn.prepareStatement(SQL);
			preps.addBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preps;
	}

}
