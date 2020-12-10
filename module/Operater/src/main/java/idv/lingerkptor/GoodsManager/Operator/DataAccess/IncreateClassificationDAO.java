package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.IncreateClassificationRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.IncreateClassificationResponse.STATECODE;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class IncreateClassificationDAO implements PreparedStatementCreator {
	private Properties prop;
	private IncreateClassificationRequest request;

	@SuppressWarnings("unused")
	private IncreateClassificationDAO() {
	}

	public IncreateClassificationDAO(Properties prop, IncreateClassificationRequest request) {
		this.prop = prop;
		this.request = request;
	}

	@SuppressWarnings("resource")
	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		/**
		 * STEP1. 查詢分類是否已建立
		 */
		String SQL = prop.getProperty("searchClassificationId");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, request.getClassificationName());
		ResultSet rs = stat.executeQuery();

		if (rs.next()) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, request.getClassificationName() + "分類已存在"));
			throw new DAORuntimeException("分類已存在", STATECODE.CLASSISEXIST);
		}
		/**
		 * STEP2. 確定上層分類
		 */
		if (!request.getParentClassificationName().isEmpty()) {
			int PCID;
			stat = conn.prepareStatement(SQL);
			stat.setString(1, request.getParentClassificationName());
			rs = stat.executeQuery();
			if (!rs.next()) {
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
						request.getParentClassificationName() + "上層分類不存在"));
				throw new DAORuntimeException("上層分類已存在", STATECODE.PARENTCLASSNONEXIST);
			}
			/**
			 * STEP3 新增分類(有上層類別的情況)
			 */
			if (request.getClassificationName().length() == 0) {
				MessageInit.getMsgManager().deliverMessage(
						new Message(Message.Category.warn, "Classification Name 不得為空字串"));
				throw new DAORuntimeException("Classification Name 不得為空字串", STATECODE.DATAERROR);
			}
			SQL = prop.getProperty("insertClassification");
			PCID = rs.getInt(1);
			stat = conn.prepareStatement(SQL);
			stat.setString(1, request.getClassificationName());
			stat.setInt(2, PCID);
			stat.executeUpdate();
		} else {
			/**
			 * STEP3 新增分類(無上層類別的情況)
			 */
			SQL = prop.getProperty("insertClassificationNonParent");
			stat = conn.prepareStatement(SQL);
			stat.setString(1, request.getClassificationName());
			stat.executeUpdate();
		}
		/**
		 * STEP4. 查詢是否完成建立分類
		 */
		SQL = prop.getProperty("searchClassificationId");
		stat = conn.prepareStatement(SQL);
		stat.setString(1, request.getClassificationName());
		rs = stat.executeQuery();

		if (!rs.next()) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, request.getClassificationName() + "分類建立失敗"));
			throw new DAORuntimeException("分類建立失敗", STATECODE.LOSTCLASS);
		}
		return stat;
	}

}
