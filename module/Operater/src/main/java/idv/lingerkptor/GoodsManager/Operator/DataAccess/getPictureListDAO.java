package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.response.GetGoodsResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class getPictureListDAO implements PreparedStatementCreator {

	private int GID = 0;
	private Properties props = new Properties();

	public getPictureListDAO(int GID) {
		this.GID = GID;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		try {
			props.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/getPictureKey.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new DAORuntimeException(e.getMessage(), GetGoodsResponse.Code.SQLFileError);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException(e.getMessage(), GetGoodsResponse.Code.SQLFileError);
		}
		String SQL = props.getProperty("getPictureKey");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setInt(1, GID);
		stat.execute();
		return stat;
	}

}
