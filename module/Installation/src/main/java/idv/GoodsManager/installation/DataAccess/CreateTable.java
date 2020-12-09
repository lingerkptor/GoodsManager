package idv.GoodsManager.installation.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import idv.GoodsManager.installation.api.response.InstallResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class CreateTable implements PreparedStatementCreator {

	private Properties sqlProp;

	public CreateTable(Properties sqlProp) {
		this.sqlProp = sqlProp;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		PreparedStatement prep;

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
			throw new DAORuntimeException("GOODS資料表已建立",
					InstallResponse.ERRORCODE.GoodsTable);
		}

		/**
		 * 確認CLASSES資料表
		 */
		SQL = sqlProp.getProperty("CHECKCLASSESTABLE");
		prep = conn.prepareStatement(SQL);
		prep.addBatch();
		rs = prep.executeQuery();
		if (!rs.next()) {// 如果CLASSES資料表不存在
			SQL = sqlProp.getProperty("Classification");
			prep = conn.prepareStatement(SQL);
			prep.addBatch();
			prep.execute();
		} else {
			throw new DAORuntimeException("CLASSES資料表已建立",
					InstallResponse.ERRORCODE.ClassesTable);
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
			throw new DAORuntimeException("PICTURE資料表已建立",
					InstallResponse.ERRORCODE.PictureTable);
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
			throw new DAORuntimeException("TAGS資料表已建立", InstallResponse.ERRORCODE.TagsTable);
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
			throw new DAORuntimeException("GOODSTAGS資料表已建立",
					InstallResponse.ERRORCODE.GoodsTagsTable);
		}
		prep.executeBatch();
		return prep;

	}

}
