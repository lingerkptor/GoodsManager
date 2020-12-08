package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.CreateClassRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.CreateClassResponse.STATECODE;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class CreateClassDAO implements PreparedStatementCreator {
	private Properties prop;
	private CreateClassRequest request;

	@SuppressWarnings("unused")
	private CreateClassDAO() {
	}

	public CreateClassDAO(Properties prop, CreateClassRequest request) {
		this.prop = prop;
		this.request = request;
	}


	@SuppressWarnings("resource")
	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		/**
		 * STEP1. 查詢分類是否已建立
		 */
		String SQL = prop.getProperty("searchClassId");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.setString(1, request.getClassName());
		ResultSet rs = stat.executeQuery();

		if (rs.next()) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, request.getClassName() + "分類已存在"));
			throw new DAORuntimeException("分類已存在", STATECODE.CLASSISEXIST);
		}
		/**
		 * STEP2. 確定上層分類
		 */
		if (!request.getParentClassName().isEmpty()) {
			int PCID;
			stat = conn.prepareStatement(SQL);
			stat.setString(1, request.getParentClassName());
			rs = stat.executeQuery();
			if (!rs.next()) {
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
						request.getParentClassName() + "上層分類不存在"));
				throw new DAORuntimeException("上層分類已存在", STATECODE.PARENTCLASSNONEXIST);
			}
			/**
			 * STEP3 新增分類(有上層類別的情況)
			 */
			SQL = prop.getProperty("insertClass");
			PCID = rs.getInt(1);
			stat = conn.prepareStatement(SQL);
			stat.setString(1, request.getClassName());
			stat.setInt(2, PCID);
			stat.execute();
		} else {
			/**
			 * STEP3 新增分類(無上層類別的情況)
			 */
			SQL = prop.getProperty("insertClassNonParent");
			stat = conn.prepareStatement(SQL);
			stat.setString(1, request.getClassName());
			stat.execute();
		}
		/**
		 * STEP4. 查詢是否完成建立分類
		 */
		SQL = prop.getProperty("searchClassId");
		stat = conn.prepareStatement(SQL);
		stat.setString(1, request.getClassName());
		rs = stat.executeQuery();

		if (!rs.next()) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, request.getClassName() + "分類建立失敗"));
			throw new DAORuntimeException("分類建立失敗", STATECODE.LOSTCLASS);
		}
		return stat;
	}

}
