package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.ModifyTagRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.ModifyTagResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class ModifyTagDAO implements PreparedStatementCreator {

	private ModifyTagRequest request;
	private Properties prop = new Properties();

	@SuppressWarnings("unused")
	private ModifyTagDAO() {
	}

	public ModifyTagDAO(ModifyTagRequest request) {
		this.request = request;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		/**
		 * Step1. 載入存放SQL的檔案
		 */
		this.loadSQLProperties();
		/**
		 * Step2. 取得TID
		 */
		int TID = this.getTagID(conn);
		/**
		 * Step3. 修改Tag名稱
		 */
		if (request.getNewTagName().length() > 0) {
			this.modifyTagName(TID, conn);
		}
		/**
		 * Step4. 修改 Tag 描述
		 */
		if (request.getTagDescription().length() > 0) {
			this.modifyTagDescription(TID, conn);
		}
		return null;
	}

	/**
	 * Step1. 載入存放SQL的檔案
	 */
	private void loadSQLProperties() {
		try {
			prop.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/UpdateTag.properties")));
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException("UpdateTag.properties Error, Message：" + e.getMessage(),
					ModifyTagResponse.CODE.SQLFILEERROR);
		}
	}

	/**
	 * Step2. 取得TID
	 * 
	 * @return TagID
	 * @throws SQLException
	 */
	private int getTagID(Connection conn) throws SQLException {
		String SQL = prop.getProperty("searchTID");
		PreparedStatement prep = conn.prepareStatement(SQL);
		prep.setString(1, request.getTagName());
		ResultSet rs = prep.executeQuery();
		if (rs.next())
			return rs.getInt(1);
		throw new DAORuntimeException("TagName " + request.getTagName() + " is NOT exist.",
				ModifyTagResponse.CODE.TAGNAMEISNOTEXIST);
	}

	/**
	 * Step3. 修改Tag名稱(包含驗證)
	 * 
	 * @param TID  TagID
	 * @param conn 資料庫連線
	 * @throws SQLException
	 */
	private void modifyTagName(int TID, Connection conn) throws SQLException {
		String SQL = prop.getProperty("modifyNameOfTAG");
		PreparedStatement prep = conn.prepareStatement(SQL);
		prep.setString(1, request.getNewTagName());
		prep.setInt(2, TID);
		prep.executeUpdate();
		// 驗證
		SQL = prop.getProperty("searchTagNamebyTID");
		prep = conn.prepareStatement(SQL);
		prep.setInt(1, TID);
		ResultSet rs = prep.executeQuery();
		if (rs.next() && rs.getString(1).equals(request.getNewTagName()))
			return;
		throw new DAORuntimeException(
				"TagName " + request.getTagName() + " 修改名稱 " + request.getNewTagName() + " 失敗",
				ModifyTagResponse.CODE.MODIFYTAGNAMEFAILURE);
	}

	/**
	 * Step4. 修改Tag描述(包含驗證)
	 * 
	 * @param TID  TagID
	 * @param conn 資料庫連線
	 * @throws SQLException
	 */
	private void modifyTagDescription(int TID, Connection conn) throws SQLException {
		String SQL = prop.getProperty("modifyDescriptionbyOfTAG");
		PreparedStatement prep = conn.prepareStatement(SQL);
		prep.setString(1, request.getTagDescription());
		prep.setInt(2, TID);
		prep.executeUpdate();
		// 驗證
		SQL = prop.getProperty("searchTagDescriptionbyTID");
		prep = conn.prepareStatement(SQL);
		prep.setInt(1, TID);
		ResultSet rs = prep.executeQuery();
		if (rs.next() && rs.getString(1).equals(request.getTagDescription()))
			return;
		throw new DAORuntimeException(
				"TagName " + request.getTagName() + " 修改名稱 " + request.getNewTagName() + " 失敗",
				ModifyTagResponse.CODE.MODIFYTAGDESCRIPTIONFAILURE);
	}
}
