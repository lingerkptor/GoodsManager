package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.ModifyGoodsRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.ModifyGoodsResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class ModifyGoodsDAO implements PreparedStatementCreator {
	private ModifyGoodsRequest request;
	private Properties props = new Properties();
	private String tagSQL;
	private String picSQL;

	public ModifyGoodsDAO(ModifyGoodsRequest request) {
		this.request = request;
		StringBuilder sqlBuilder = new StringBuilder(" ( ");
		for (int i = 0; i < request.getTags().length; i++) {
			sqlBuilder.append(" ? ");
			if (i != request.getTags().length - 1)
				sqlBuilder.append(" , ");
		}
		sqlBuilder.append(" ) ");
		this.tagSQL = sqlBuilder.toString();
		sqlBuilder = new StringBuilder(" ( ");
		for (int i = 0; i < request.getPictureKey().length; i++) {
			sqlBuilder.append(" ? ");
			if (i != request.getPictureKey().length - 1)
				sqlBuilder.append(" , ");
		}
		sqlBuilder.append(" ) ");
		this.picSQL = sqlBuilder.toString();
	}

	private void loadProps() {
		try {
			props.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/ModifyGoods.properties")));
		} catch (IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException(e.getMessage(), ModifyGoodsResponse.Code.SQLFileError);
		}
	}

	private void updateGoods(Connection conn) throws SQLException {
		String SQL = props.getProperty("updateGoods");
		PreparedStatement stat = conn.prepareStatement(SQL);
		int parameterIndex = 1;
		stat.setString(parameterIndex++, request.getGoodsName());
		stat.setDouble(parameterIndex++, request.getL());
		stat.setDouble(parameterIndex++, request.getW());
		stat.setDouble(parameterIndex++, request.getH());
		stat.setInt(parameterIndex++, request.getPrice());
		stat.setInt(parameterIndex++, request.getCount());
		stat.setString(parameterIndex++, request.getClassName());
		stat.setDate(parameterIndex++, new java.sql.Date(Long.valueOf(request.getDate())));
		stat.setInt(parameterIndex++, request.getId());
		stat.executeUpdate();
	}

	private void updateExceptTags(Connection conn) throws SQLException {
		int parameterIndex = 1;
		String SQL = props.getProperty("deleteGoodsTags");
		PreparedStatement stat = conn.prepareStatement(SQL + this.tagSQL + " ) ;");
		stat.setInt(parameterIndex++, request.getId());
		for (String tag : request.getTags()) {
			stat.setString(parameterIndex++, tag);
		}
		stat.executeUpdate();
	}

	private void insertTags(Connection conn) throws SQLException {
		int parameterIndex = 1;
		StringBuilder sqlBuilder = new StringBuilder(props.getProperty("insertGoodsTags"));
		sqlBuilder.append(this.tagSQL + props.getProperty("insertGoodsTags2"));
		PreparedStatement stat = conn.prepareStatement(sqlBuilder.toString());
		stat.setInt(parameterIndex++, request.getId());
		for (String tag : request.getTags()) {
			stat.setString(parameterIndex++, tag);
		}
		stat.setInt(parameterIndex++, request.getId());
		stat.executeUpdate();
	}

	private void unbindExceptPicture(Connection conn) throws SQLException {
		int parameterIndex = 1;
		PreparedStatement stat = conn
				.prepareStatement(props.getProperty("unbindPicture") + this.picSQL + " ;");
		stat.setInt(parameterIndex++, request.getId());
		for (String picKey : request.getPictureKey()) {
			stat.setString(parameterIndex++, picKey);
		}
		stat.executeUpdate();
	}

	private void insertPictureKey(Connection conn) throws SQLException {
		int parameterIndex = 1;
		PreparedStatement stat = conn
				.prepareStatement(props.getProperty("bindPicture") + this.picSQL + " ;");
		stat.setInt(parameterIndex++, request.getId());
		for (String picKey : request.getPictureKey()) {
			stat.setString(parameterIndex++, picKey);
		}
		stat.executeUpdate();
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		this.loadProps();
		this.updateGoods(conn);
		this.updateExceptTags(conn);
		this.insertTags(conn);
		this.unbindExceptPicture(conn);
		this.insertPictureKey(conn);

		return null;
	}

}
