package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import idv.lingerkptor.GoodsManager.Operator.api.request.IncreateGoodsRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.IncreateGoodsResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class IncreateGoodsDAO implements PreparedStatementCreator {
	private Properties sqlProp;
	private IncreateGoodsRequest request = null;

	@SuppressWarnings("unused")
	private IncreateGoodsDAO() {
	}

	public IncreateGoodsDAO(IncreateGoodsRequest request) {
		this.request = request;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		/** Step1. 載入存放SQL的檔案 */
		this.loadProperties();
		/** Step2. 確認圖片是否已經綁定商品 */
		this.pictureisBindedGoodsCheck(conn);

		/** Step3. 查詢分類ID */
		int CID = this.searchCID(conn);
		/** Step4. 新增商品 */
		int GID = this.increateGoods(conn, CID);
		/** Step5. 對圖片進行綁定 */
		this.bindingPicture(conn, GID);
		/** Step6. 標籤綁定 */
		this.bindingTags(conn, GID);

		return null;
	}

	private void loadProperties() {
		sqlProp = new Properties();
		try {
			sqlProp.load(new FileReader( //
					new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/IncreateGoods.properties")));
		} catch (NullPointerException | IOException e) {
			throw new DAORuntimeException(e.getMessage(), IncreateGoodsResponse.Code.SQLFILEERROR);
		}
	}

	private void pictureisBindedGoodsCheck(Connection conn) throws SQLException {
		if (request.getPictureKey() == null)
			return;
		StringBuilder strbuilder = new StringBuilder(
				sqlProp.getProperty("searchPictureIsBindedGoods"));
		for (int i = 0; i < request.getPictureKey().length; i++) {
			if (i == request.getPictureKey().length - 1)
				strbuilder.append(" ? ) ;");
			else
				strbuilder.append("? , ");
		}
		PreparedStatement stat = conn.prepareStatement(strbuilder.toString());

		for (int i = 0; i < request.getPictureKey().length; i++) {
			stat.setString(i + 1, request.getPictureKey()[i]);
		}
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			if (rs.getInt(2) > 0)
				throw new DAORuntimeException("Picture has been binded Goods.",
						IncreateGoodsResponse.Code.PICTUREISBINDED);
		}
	}

	private int searchCID(Connection conn) throws SQLException {
		String sql = sqlProp.getProperty("searchClassificationId");
		PreparedStatement stat = conn.prepareStatement(sql);
		stat.setString(1, request.getClassName());
		ResultSet rs = stat.executeQuery();
		if (rs.next())
			return rs.getInt(1);
		else
			throw new DAORuntimeException("新增商品錯誤，分類名稱不存在．",
					IncreateGoodsResponse.Code.CLASSNONEXIST);

	}

	private int increateGoods(Connection conn, int CID) throws SQLException {
		String sql = sqlProp.getProperty("insertGoods");
		PreparedStatement stat = conn.prepareStatement(sql);
		stat = conn.prepareStatement(sql);
		stat.setString(1, request.getgName());
		stat.setDouble(2, request.getL());
		stat.setDouble(3, request.getW());
		stat.setDouble(4, request.getH());
		stat.setInt(5, request.getPrice());
		stat.setInt(6, request.getCount());
		stat.setInt(7, CID);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		stat.setDate(8, java.sql.Date.valueOf(format.format(new java.util.Date())));
		stat.executeUpdate();

		sql = sqlProp.getProperty("searchGoodsId");
		stat = conn.prepareStatement(sql);
		ResultSet rs = stat.executeQuery();
		if (rs.next())
			return rs.getInt(1);
		else {
			throw new DAORuntimeException("建立商品錯誤，Message: 遺失已建立的商品．",
					IncreateGoodsResponse.Code.LOSTGOODS);
		}

	}

	private void bindingPicture(Connection conn, int GID) throws SQLException {
		String sql = sqlProp.getProperty("bindPicture");
		PreparedStatement stat = conn.prepareStatement(sql);
		for (String key : request.getPictureKey()) {
			stat.setInt(1, GID);
			stat.setString(2, key);
			stat.addBatch();
		}
		stat.executeBatch();
	}

	private void bindingTags(Connection conn, int GID) throws SQLException {
		if (request.getTags() == null)
			return;
		/** 查詢TID start */
		StringBuilder sql = new StringBuilder(sqlProp.getProperty("searchTagID"));

		for (int i = 0; i < request.getTags().length; i++) {
			if (i == request.getTags().length - 1)
				sql.append(" ? ) ;");
			else
				sql.append(" ? , ");
		}

		PreparedStatement stat = conn.prepareStatement(sql.toString());
		for (int i = 0; i < request.getTags().length; i++) {
			stat.setString(i + 1, request.getTags()[i]);
		}
		ResultSet rs = stat.executeQuery();
		Set<Integer> TIDs = new HashSet<Integer>();
		while (rs.next()) {
			TIDs.add(rs.getInt(1));
		}
		/** 查詢TID end */

		String SQL = sqlProp.getProperty("mapGoodsTag");
		stat = conn.prepareStatement(SQL);
		for (Integer TID : TIDs) {
			stat.setInt(1, GID);
			stat.setInt(2, TID);
			stat.addBatch();
		}
		stat.executeBatch();
	}
}
