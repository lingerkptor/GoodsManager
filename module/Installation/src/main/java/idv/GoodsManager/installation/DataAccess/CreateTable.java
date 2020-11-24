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
		StringBuilder exceptionMsg = new StringBuilder();

		/**
		 * 確認GOODS資料表
		 */
		String SQL = sqlProp.getProperty("CHECKGOODSTABLE");
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		ResultSet rs = prep.executeQuery();
		if (!rs.next()) {// 如果GOODS資料表不存在
			SQL = sqlProp.getProperty("GOODS");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			prep.execute();
		} else {
			exceptionMsg.append("GOODS資料表已建立、");
		}

		/**
		 * 確認CLASSES資料表
		 */
		SQL = sqlProp.getProperty("CHECKCLASSESTABLE");
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果CLASSES資料表不存在
			SQL = sqlProp.getProperty("CLASSES");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			prep.execute();
		} else {
			exceptionMsg.append("CLASSES資料表已建立、");
		}

		/**
		 * 確認PICTURE資料表
		 */
		SQL = sqlProp.getProperty("CHECKPICTURETABLE");
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果PICTURE資料表不存在
			SQL = sqlProp.getProperty("PICTURE");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			prep.execute();
		} else {
			exceptionMsg.append("PICTURE資料表已建立、");
		}

		/**
		 * 確認TAGS資料表
		 */
		SQL = sqlProp.getProperty("CHECKTAGSTABLE");
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果TAGS資料表不存在
			SQL = sqlProp.getProperty("TAGS");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			prep.execute();
		} else {
			exceptionMsg.append("TAGS資料表已建立、");
		}

		/**
		 * 確認GOODSTAGS資料表
		 */
		SQL = sqlProp.getProperty("CHECKGOODSTAGSTABLE");
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果GOODSTAGS資料表不存在
			SQL = sqlProp.getProperty("GOODSTAGS");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
		} else {
			exceptionMsg.append("GOODSTAGS資料表已建立");
		}
		if (!(exceptionMsg.toString().isEmpty()))
			throw new SQLException(exceptionMsg.toString());
		return prep;

	}

}
