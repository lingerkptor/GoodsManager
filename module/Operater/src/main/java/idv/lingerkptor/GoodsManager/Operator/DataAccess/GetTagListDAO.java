package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.DataObject.Tag;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetTagListResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;
import idv.lingerkptor.util.DBOperator.RowCallbackHandler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class GetTagListDAO implements PreparedStatementCreator, RowCallbackHandler {
	private Properties prop = new Properties();
	private GetTagListResponse response = GetTagListResponse.STATECODE.SUCCESS.getResponse();

	/**
	 * Step1. 載入存放SQL的檔案
	 */
	private void LoadSQLFile() {
		try {
			prop.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/GetTagList.properties")));
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException("檔案GetTagList.properties出問題 Message：" + e.getMessage(),
					GetTagListResponse.STATECODE.SQLFILEERROR);
		}
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		this.LoadSQLFile();
		String SQL = prop.getProperty("listAllTag");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.addBatch();
		stat.execute();
		return stat;
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		response.addTag(new Tag(rs.getString(1), rs.getString(2), rs.getInt(3)));
	}

	public GetTagListResponse getResponse() {
		return response;
	}

}
