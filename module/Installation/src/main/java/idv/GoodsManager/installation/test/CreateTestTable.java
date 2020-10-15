package idv.GoodsManager.installation.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class CreateTestTable implements PreparedStatementCreator {

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) {
		String SQL = "Create Table Test ( test char(20) PRIMARY KEY, inttest Integer);";
		try {
			PreparedStatement prps = conn.prepareStatement(SQL);
			prps.addBatch();
			return prps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
