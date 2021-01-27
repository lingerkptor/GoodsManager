package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.GetGoodsRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetGoodsResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class GetGoodsDAO implements PreparedStatementCreator {
	private GetGoodsRequest request;
	private Properties props = new Properties();

	public GetGoodsDAO(GetGoodsRequest request) {
		this.request = request;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		try {
			props.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/GetGoods.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new DAORuntimeException(e.getMessage(), GetGoodsResponse.Code.SQLFileError);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException(e.getMessage(), GetGoodsResponse.Code.SQLFileError);
		}
		String SQL = props.getProperty("getGoods");
//		System.out.println("GetGoods SQL=> " + SQL);
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setInt(1, request.getGID());
		stat.execute();
		return stat;
	}

}
