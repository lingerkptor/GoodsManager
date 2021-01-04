package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.UploadPictureRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.UploadPictureResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.PictureManager;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class UploadPictureDAO implements PreparedStatementCreator {
	private Properties prop = new Properties();
	private UploadPictureRequest request;
	private List<String> keyList = new LinkedList<String>();

	public UploadPictureDAO(UploadPictureRequest request) {
		this.request = request;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {

		/**
		 * Step1. 載入存放SQL的檔案
		 */
		this.loadSQLFile();

		/**
		 * Step2. 逐一存入存入資料庫
		 */
		for (File file : request.getPictureFile()) {
			try {
				/**
				 * Step3. 交給PictureManager儲存下來 <br/>
				 * key = 取得圖片用的關鍵碼
				 */
				String key = PictureManager.getPictureManager().savePicture(file);
				keyList.add(key);
				/**
				 * Step4. 查詢是否有相同的圖片
				 */
				String SQL = prop.getProperty("searchPicturebyKey");
				PreparedStatement stat = conn.prepareStatement(SQL);
				stat.setString(1, key);
				ResultSet rs = stat.executeQuery();
				if (rs.next())
					continue;
				/**
				 * Step4. 存入資料庫
				 */
				SQL = prop.getProperty("insertPicture");
				stat = conn.prepareStatement(SQL);
				stat.setString(1, key);
				stat.executeUpdate();
			} catch (RuntimeException e) {
				throw new DAORuntimeException(e.getMessage(),
						UploadPictureResponse.Code.STOREFAILURE);
			}
		}

		return null;
	}

	/**
	 * Step1. 載入存放SQL的檔案
	 */
	private void loadSQLFile() {
		try {
			prop.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/UploadPicture.properties")));
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "SQL檔案出問題 Message：" + e.getMessage()));
			throw new DAORuntimeException("SQL檔案出問題 Message：" + e.getMessage(),
					UploadPictureResponse.Code.SQLFILEERROR);
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getKeyList() {
		return this.keyList;
	}

}
