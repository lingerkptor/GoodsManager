package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import idv.lingerkptor.GoodsManager.Operator.api.request.IncreateTagRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.IncreateTagResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class IncreateTagDAO implements PreparedStatementCreator {
	private IncreateTagRequest request;
	private Properties prop = new Properties();

	public IncreateTagDAO(IncreateTagRequest request) {
		this.request = request;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		// STEP1. 載入存放SQL語句的檔案
		this.loadSQLFile();
		// STEP2. 確認是否已存在相同的標籤
		if (this.checkTagisExist(conn))
			throw new DAORuntimeException("標籤名：" + request.getTagName() + "已經存在．",
					IncreateTagResponse.Code.TAGISEXIST);
		// STEP3. 建立標籤
		this.increateTag(conn);
		// STEP4. 確認是否建立成功
		if (!this.checkTagisExist(conn))
			throw new DAORuntimeException("標籤名：" + request.getTagName() + "建立失敗．",
					IncreateTagResponse.Code.INCREATEFAILURE);
		return null;
	}

	/**
	 * STEP1. 載入存放SQL語句的檔案
	 * 
	 * @throws DAORuntimeException 檔案載入有問題時拋出
	 */
	private void loadSQLFile() throws DAORuntimeException {
		try {
			prop.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/IncreateTag.properties")));
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException("SQL檔案出問題 Message：" + e.getMessage(),
					IncreateTagResponse.Code.SQLFILEERROR);
		}
	}

	/**
	 * STEP2. 確認是否已存在相同的Tag <br/>
	 * STEP4. 確認是否建立成功
	 * 
	 * @param conn 資料庫連接
	 * @throws SQLException        SQL出現問題
	 * @throws DAORuntimeException 有相同的TagName 存在在資料庫．
	 */
	private boolean checkTagisExist(Connection conn) throws SQLException {
		String SQL = prop.getProperty("searchTID");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, request.getTagName());
		ResultSet rs = stat.executeQuery();
		if (rs.next())
			return true;
		return false;
	}

	/**
	 * STEP3. 建立標籤
	 * 
	 * @param conn 資料庫連接
	 * @throws SQLException SQL出現問題
	 */
	private void increateTag(Connection conn) throws SQLException {
		String SQL = prop.getProperty("insertNewTag");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, request.getTagName());
		stat.setString(2, request.getTagDescription());
		stat.executeUpdate();
	}

}
