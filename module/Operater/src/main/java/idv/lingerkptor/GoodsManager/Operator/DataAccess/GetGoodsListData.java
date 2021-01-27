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
			// 條件 className
			if (request.getClassName() != null && (!request.getClassName().isEmpty())) {
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
						return ++parameterIndex;
					}
				});
			}
			// 條件 goods name keyword
			if (request.getKeyword() != null && (!request.getKeyword().isEmpty())) {
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
						return ++parameterIndex;
					}

				});
			}
			// 條件: tag names
			if (request.getTags() != null && request.getTags().length > 0) {
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

			// 條件:指定時間
			if ((request.getDate() != null && (!request.getDate().isEmpty()))) {
				conditions.add(new SQLCondition() {
					@Override
					public void appendCondition(StringBuilder builder) {
						builder.append(" ");
						builder.append(SQLProp.getProperty("conditionDate"));
						builder.append(" ");
					}

					@Override
					public int insertData(PreparedStatement stat, int parameterIndex)
							throws SQLException {
						stat.setDate(parameterIndex, java.sql.Date.valueOf(request.getDate()));
						return ++parameterIndex;
					}
				});
			}
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
		/**
		 * 排序+分頁
		 */
		SQLCondition sortable_page = new SQLCondition() {
			@Override
			public void appendCondition(StringBuilder builder) {
				String order = "id";
				String sorting = " ASC ";
				switch (request.getSortIn()) {
				case 1:
					sorting = " DESC ";
					break;
				default:
					break;
				}

				if (request.getOrder() != null)
					order = request.getOrder();

				switch (order) {
				case "class":
					builder.append(SQLProp.getProperty("coditionSortbyClass"));
					builder.append(sorting);
					builder.append(" , GID ASC ");

					break;
				case "date":
					builder.append(SQLProp.getProperty("coditionSortbyDate"));
					builder.append(sorting);
					builder.append(" , GID ASC ");
					break;
				default:
					builder.append(SQLProp.getProperty("conditionCustomsToken"));
					builder.append(sorting);
					break;
				}
				searchGoodsList.append(SQLProp.getProperty("coditionCount"));

			}

			@Override
			public int insertData(PreparedStatement stat, int parameterIndex) throws SQLException {
//				System.out.println(request.getPage());
//				System.out.println(request.getCount());
//				System.out.println((request.getPage() - 1) * request.getCount());
				stat.setInt(parameterIndex++, request.getCount());
				stat.setInt(parameterIndex++, (request.getPage() - 1) * request.getCount());
				return parameterIndex;
			}

		};
		conditions.add(sortable_page);
		sortable_page.appendCondition(searchGoodsList);

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
