package idv.GoodsManager.installation.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class CreateTable implements PreparedStatementCreator {

	private Properties sqlProp;

	public CreateTable(Properties sqlProp) {
		this.sqlProp = sqlProp;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		PreparedStatement prep;
		//
		String SQL = sqlProp.getProperty("CHECKGOODSTABLE");
		System.out.println("CHECKGOODSTABLE SQL = " + SQL);
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		ResultSet rs = prep.executeQuery();
		if (!rs.next()) {// 如果存在
			SQL = sqlProp.getProperty("GOODS");
			System.out.println("Goods SQL = " + SQL);
			prep.addBatch();
			prep.execute();
		}
		SQL = sqlProp.getProperty("CHECKCLASSESTABLE");
		System.out.println("CHECKCLASSESTABLE SQL = " + SQL);
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果存在
			SQL = sqlProp.getProperty("CLASSES");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			prep.execute();
		}
		SQL = sqlProp.getProperty("CHECKPICTURETABLE");
		System.out.println("CHECKPICTURETABLE SQL = " + SQL);
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果存在
			SQL = sqlProp.getProperty("PICTURE");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			prep.execute();
		}
		SQL = sqlProp.getProperty("CHECKTAGSTABLE");
		System.out.println("CHECKTAGSTABLE SQL = " + SQL);
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果存在
			SQL = sqlProp.getProperty("TAGS");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			prep.execute();
		}
		SQL = sqlProp.getProperty("CHECKGOODSTAGSTABLE");
		System.out.println("CHECKGOODSTAGSTABLE SQL = " + SQL);
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果存在
			SQL = sqlProp.getProperty("GOODSTAGS");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
		}
		return prep;
	}

}
