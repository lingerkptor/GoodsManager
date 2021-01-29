package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.DeleteGoodsRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.DeleteGoodsResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class DeleteGoodsDAO implements PreparedStatementCreator {
	private DeleteGoodsRequest request;
	private Properties props = new Properties();

	public DeleteGoodsDAO(DeleteGoodsRequest request) {
		this.request = request;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		this.loadProps();
		this.unbindPicture(conn);
		this.deleteGoodsTags(conn);
		this.deleteGoods(conn);
		return null;
	}

	private void loadProps() {
		try {
			props.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/DeleteGoods.properties")));
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException(e.getMessage(), DeleteGoodsResponse.Code.SQLFileError);
		}
	}

	private void unbindPicture(Connection conn) throws SQLException {
		String SQL = props.getProperty("unbindPicture");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setInt(1, request.getGID());
		stat.executeUpdate();
	}

	private void deleteGoodsTags(Connection conn) throws SQLException {
		String SQL = props.getProperty("deleteGoodsTag");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setInt(1, request.getGID());
		stat.executeUpdate();
	}

	private void deleteGoods(Connection conn) throws SQLException {
		String SQL = props.getProperty("deleteGoods");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setInt(1, request.getGID());
		stat.executeUpdate();
	}
}
