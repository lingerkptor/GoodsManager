package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.GetGoodsListRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetGoodsListResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class GetGoodsListData implements PreparedStatementCreator {

	private Properties SQLProp = new Properties();
	private GetGoodsListRequest request;
	private List<SQLCondition> conditions = new LinkedList<SQLCondition>();

	public GetGoodsListData(GetGoodsListRequest request) {
		this.request = request;
	}

	/**
	 * Step1. 載入存放SQL的檔案
	 */
	private void loadSQLProperties() {
		try {
			SQLProp.load(new FileInputStream(
					new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/GetGoodsList.properties")));
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException(e.getMessage(), GetGoodsListResponse.Code.SQLFileError);
		}
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		// Step1. 載入存放SQL的檔案
		this.loadSQLProperties();
		// Step2. SQL
		StringBuilder searchGoodsList = new StringBuilder(SQLProp.getProperty("selectGoodsData"));

		// Step3. 加入要處理的條件
		if (request != null) {
			// className filter condition
			if (request.getClassName() != null) {
				conditions.add(new SQLCondition() {

					@Override
					public void appendCondition(StringBuilder builder) {
						builder.append(" ");
						builder.append(SQLProp.getProperty("conditionClassification"));
						builder.append(" ");
					}

					@Override
					public int insertData(PreparedStatement stat, int parameterIndex)
							throws SQLException {
						stat.setString(parameterIndex, request.getClassName());
						return parameterIndex + 1;
					}
				});
			}
			// goods name keyword filter condition
			if (request.getKeyword() != null) {
				conditions.add(new SQLCondition() {

					@Override
					public void appendCondition(StringBuilder builder) {
						builder.append(" ");
						builder.append(SQLProp.getProperty("conditionGoodsNameKeyword"));
						builder.append(" ");
					}

					@Override
					public int insertData(PreparedStatement stat, int parameterIndex)
							throws SQLException {
						stat.setString(parameterIndex, "%" + request.getKeyword() + "%");
						return parameterIndex + 1;
					}

				});
			}
			// tag names filter condition
			if (request.getTags() != null) {
				conditions.add(new SQLCondition() {

					@Override
					public void appendCondition(StringBuilder builder) {
						builder.append(" ");
						builder.append(SQLProp.getProperty("conditionTagGID"));
						builder.append(" ");
						for (int i = 0; i < request.getTags().length; i++) {
							if (i == request.getTags().length - 1)
								builder.append(" ? ) ");
							else
								builder.append(" ? , ");
						}
						builder.append("  ) ");
					}

					@Override
					public int insertData(PreparedStatement stat, int parameterIndex)
							throws SQLException {
						for (String tagName : request.getTags()) {
							stat.setString(parameterIndex++, tagName);
						}
						return parameterIndex;
					}
				});
			}
		}
		if ((request != null) && (request.getToken() != null)) {
			conditions.add(new SQLCondition() {

				@Override
				public void appendCondition(StringBuilder builder) {
					builder.append(SQLProp.getProperty("conditionCustomsToken"));
				}

				@Override
				public int insertData(PreparedStatement stat, int parameterIndex)
						throws SQLException {
					stat.setInt(parameterIndex, Integer.valueOf(request.getToken()));
					return parameterIndex++;
				}

			});
		} else {
			conditions.add(new SQLCondition() {

				@Override
				public void appendCondition(StringBuilder builder) {
					builder.append(SQLProp.getProperty("conditionDefaultToken"));
				}

				@Override
				public int insertData(PreparedStatement stat, int parameterIndex)
						throws SQLException {
					return parameterIndex;
				}

			});
		}

		Iterator<SQLCondition> iterator = conditions.iterator();
		boolean hasNaxt = iterator.hasNext();
		if (hasNaxt) {
			searchGoodsList.append(" WHERE ");
		}
		while (hasNaxt) {
			SQLCondition con = iterator.next();
			con.appendCondition(searchGoodsList);

			if (hasNaxt = iterator.hasNext()) {
				searchGoodsList.append(" AND ");
			}
		}

		searchGoodsList.append(" ;");
//		System.out.println("searchGoodsList String=>" + searchGoodsList.toString());
		// SQL語句組合完成
		// Step5. 開始插入資料
		PreparedStatement stat = conn.prepareStatement(searchGoodsList.toString());
		int index = 1;
		for (SQLCondition con : conditions) {
			index = con.insertData(stat, index);
		}
		stat.addBatch();
		stat.execute();
		return stat;
	}

}
