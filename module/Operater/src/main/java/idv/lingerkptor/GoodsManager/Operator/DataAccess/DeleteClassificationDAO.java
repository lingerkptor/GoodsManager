package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.DeleteClassificationRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.DeleteClassificationResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class DeleteClassificationDAO implements PreparedStatementCreator {

	private DeleteClassificationRequest request;
	private Properties prop = new Properties();

	public DeleteClassificationDAO(DeleteClassificationRequest request) {
		this.request = request;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		PreparedStatement stat;
		// STEP1. 載入存放SQL語句的檔案
		this.step1();
		// STEP2. 取得要刪除分類的CID、取得上層類別的CID
		int[] result = this.step2(conn);
		// STEP3. 將下層分類安排在上層分類之下
		this.step3(conn, result[0], result[1]);
		// STEP3.1 檢查是否將下層分類安排在上層分類之下
		try {
			String SQL = prop.getProperty("checkUpdateSubClassifiction");
			stat = conn.prepareStatement(SQL);
			stat.setInt(1, result[0]);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
						request.getClassificationName() + "子分類交付管理失敗"));
				throw new DAORuntimeException(request.getClassificationName() + "子分類交付管理失敗",
						DeleteClassificationResponse.STATECODE.DELETEFAILURE);
			}
		} catch (SQLException e) {
			System.err.println("STEP6.  " + request.getClassificationName() + "子分類交付管理失敗");
			e.printStackTrace();
			throw e;
		}

		// STEP4. 如果有上層分類，就將底下的商品交由上層分類管理
		this.step4(conn, result[0], result[1]);
		// STEP4.1 檢查是否將底下的商品交由上層分類管理
		try {
			String SQL = prop.getProperty("checkUpdateClassificationOfGoods");
			stat = conn.prepareStatement(SQL);
			stat.setInt(1, result[0]);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
						"分類" + request.getClassificationName() + "商品交付管理失敗"));
				throw new DAORuntimeException("分類" + request.getClassificationName() + "商品交付管理失敗",
						DeleteClassificationResponse.STATECODE.DELETEFAILURE);
			}
		} catch (SQLException e) {
			System.err.println("STEP6. 檢查GOODS是否交由上層分類管理 出錯");
			e.printStackTrace();
			throw e;
		}
		// STEP5. 刪除分類
		this.step5(conn, result[0]);
		// STEP5.1 檢查是否刪除分類
		try {
			String SQL = prop.getProperty("searchClassificationId");
			stat = conn.prepareStatement(SQL);
			stat.setString(1, request.getClassificationName());
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
						"分類" + request.getClassificationName() + "刪除失敗"));
				throw new DAORuntimeException("",
						DeleteClassificationResponse.STATECODE.DELETEFAILURE);
			}
		} catch (SQLException e) {
			System.err.println("STEP6. 檢查分類" + request.getClassificationName() + "是否刪除 出錯");
			e.printStackTrace();
			throw e;
		}
		return stat;
	}

	/**
	 * STEP1. 載入存放SQL語句的檔案
	 */
	private void step1() {
		try {
			prop.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/DeleteClassification.properties")));
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "SQL檔案出問題 Message：" + e.getMessage()));
			throw new DAORuntimeException("SQL檔案出問題 Message：" + e.getMessage(),
					DeleteClassificationResponse.STATECODE.SQLFILEERROR);
		}
	}

	/**
	 * STEP2. 取得要刪除分類的CID、取得上層類別的CID
	 * 
	 * @throws SQLException
	 */
	private int[] step2(Connection conn) throws SQLException {
		int[] result = { 0, 0 };// result[0]=CID, result[1]=PID
		try {
			String SQL = prop.getProperty("searchClassificationId");
			PreparedStatement stat = conn.prepareStatement(SQL);
			stat.setString(1, request.getClassificationName());
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				result[0] = rs.getInt(1);
				result[1] = rs.getInt(2);
			} else {
				MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
						"找不到" + request.getClassificationName() + "分類 "));
				throw new DAORuntimeException("找不到" + request.getClassificationName() + "分類 ",
						DeleteClassificationResponse.STATECODE.CLASSIFICATIONNONEXIST);
			}
		} catch (SQLException e) {
			System.err.println("STEP2. 取得要刪除分類的CID、取得上層類別的CID 出錯");
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * STEP3. 將下層分類安排在上層分類之下
	 * 
	 * @throws SQLException
	 */
	private void step3(Connection conn, int CID, int PID) throws SQLException {
		try {
			String SQL = prop.getProperty("modifySubClassification");
			PreparedStatement stat = conn.prepareStatement(SQL);
			stat.setInt(1, PID);
			stat.setInt(2, CID);
			stat.executeUpdate();
		} catch (SQLException e) {
			System.err.println("STEP3. 將下層分類安排在上層分類之下 出錯");
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * STEP4. 將底下的商品交由上層分類管理
	 * 
	 * @throws SQLException
	 */
	private void step4(Connection conn, int CID, int PID) throws SQLException {
		if (PID != 0)
			try {
				String SQL = prop.getProperty("modifyClassificationOfGoods");
				PreparedStatement stat = conn.prepareStatement(SQL);
				stat.setInt(1, PID);
				stat.setInt(2, CID);
				stat.executeUpdate();
			} catch (SQLException e) {
				System.err.println("STEP4.如果有上層分類，就將底下的商品交由上層分類管理 出錯");
				e.printStackTrace();
				throw e;
			}
	}

	/**
	 * STEP5. 刪除分類
	 * 
	 * @throws SQLException
	 */
	private void step5(Connection conn, int CID) throws SQLException {
		try {
			String SQL = prop.getProperty("deleteClassification");
			PreparedStatement stat = conn.prepareStatement(SQL);
			stat.setInt(1, CID);
			stat.executeUpdate();
		} catch (SQLException e) {
			System.err.println("STEP4.如果有上層分類，就將底下的商品交由上層分類管理 出錯");
			e.printStackTrace();
			throw e;
		}
	}
}
