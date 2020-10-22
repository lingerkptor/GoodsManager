package idv.GoodsManager.installation.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class CreateTable implements PreparedStatementCreator {

	private Properties sqlProp;

	public CreateTable(Properties sqlProp) {
		this.sqlProp = sqlProp;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) {
		try {
			String SQL = sqlProp.getProperty("GOODS");
			System.out.println("Goods SQL = " + SQL);
			PreparedStatement prep = conn.prepareStatement(SQL);
			prep.addBatch();
			SQL = sqlProp.getProperty("CLASSES");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			SQL = sqlProp.getProperty("PICTURE");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			SQL = sqlProp.getProperty("TAGS");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			SQL = sqlProp.getProperty("GOODSTAGS");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			return prep;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
