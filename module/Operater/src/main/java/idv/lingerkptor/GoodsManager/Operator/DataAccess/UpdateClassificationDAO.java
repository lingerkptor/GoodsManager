package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.Operator.api.request.UpdateClassificationRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.UpdateClassificationResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

public class UpdateClassificationDAO implements PreparedStatementCreator {

	private Properties prop = new Properties();
	private UpdateClassificationRequest request;

	@SuppressWarnings("unused")
	private UpdateClassificationDAO() {

	}

	public UpdateClassificationDAO(UpdateClassificationRequest request) {
		this.request = request;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		PreparedStatement stat = null;
		// Step1. Load SQLFile to Properties
		this.Step1LoadSQL();

		// Step2 GET Update CID
		int CID = this.step2GetUpdateCID(conn, request.getClassificationName());

		// Step3. Check Update content
		int updateContent = request.getUpdateContent();
		switch (updateContent) {
		case 0:
			throw new DAORuntimeException("沒有想要更新請別發這個請求，謝謝．或是原始碼有問題．",
					UpdateClassificationResponse.STATECODE.NONUPDATECLASSIFICATION);
		case 3: // Step4.3 更新分類名稱以及上層分類
		case 1: // Step4.1 只要更新分類名稱
			this.Step4_1UpdateName(conn, CID, request.getClassificationNewName());
			if (updateContent == 1)
				break;
		case 2: // Step4.2 只要更新上層分類
			this.Step4_2UpdateParent(conn, CID, request.getParentClassificationName());
			break;
		default:
			throw new DAORuntimeException("請檢查原始碼，有問題．",
					UpdateClassificationResponse.STATECODE.OTHEREXCEPTION);
		}
		return stat;
	}

	/**
	 * Step1. 載入SQL檔案內容
	 */
	private void Step1LoadSQL() {
		try {
			prop.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/UpdateClassification.properties")));
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			throw new DAORuntimeException("SQL檔案出問題 Message：" + e.getMessage(),
					UpdateClassificationResponse.STATECODE.SQLFILEERROR);
		}
	}

	/**
	 * Step2. 取得分類ID
	 * 
	 * @param conn               資料庫連接
	 * @param classificationName 分類名稱
	 * @return 分類ID
	 */
	private int step2GetUpdateCID(Connection conn, String classificationName) throws SQLException {
		try {
			String SQL = prop.getProperty("searchClassificationId");
			PreparedStatement prep = conn.prepareStatement(SQL);
			prep.setString(1, classificationName);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getInt(1));
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "取得分類ID，有問題．"));
			throw e;
		}
		throw new DAORuntimeException("找不到" + classificationName + "分類",
				UpdateClassificationResponse.STATECODE.NONUPDATECLASSIFICATION);
	}

	/**
	 * Step4.1 更新分類名稱
	 * 
	 * @param conn    資料庫連接
	 * @param CID     分類ID
	 * @param newName 新名稱
	 */
	private void Step4_1UpdateName(Connection conn, int CID, String newName) throws SQLException {
		try {
			try {
				this.step2GetUpdateCID(conn, newName);
			} catch (DAORuntimeException e) {
				// 更新分類名稱
				String SQL = prop.getProperty("modifyNameOfClassification");
				PreparedStatement prep = conn.prepareStatement(SQL);
				prep.setString(1, newName);
				prep.setInt(2, CID);
				prep.addBatch();
				prep.executeBatch();
				// 檢查是否更新成功
				SQL = prop.getProperty("searchC_NamebyCID");
				prep = conn.prepareStatement(SQL);
				prep.setInt(1, CID);
				ResultSet rs = prep.executeQuery();
				if (!rs.next()) {
					throw new DAORuntimeException("更新分類名稱，有問題．找不到更新後的分類",
							UpdateClassificationResponse.STATECODE.NONUPDATECLASSIFICATION);
				}
				if (!rs.getString(1).equals(newName))
					throw new DAORuntimeException("更新分類名稱，有問題．沒有更新名稱成功",
							UpdateClassificationResponse.STATECODE.UPDATENAMEFAILURE);
				return;
			}
			throw new DAORuntimeException(newName + "名稱已存在",
					UpdateClassificationResponse.STATECODE.NEWNAMEISEXIST);
		} catch (SQLException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "更新分類名稱，有SQLException問題．"));
			throw e;
		}

	}

	/**
	 * Step4.2 更新上層分類
	 * 
	 * @param conn                     資料庫連接
	 * @param CID                      分類ID
	 * @param parentClassificationName
	 * @throws SQLException
	 */
	private void Step4_2UpdateParent(Connection conn, int CID, String parentClassificationName)
			throws SQLException {
		// 取得要更改的上層分類ID
		Integer PID = this.step2GetUpdateCID(conn, parentClassificationName);
		// 取得所有的下層分類
		List<Integer> allSubClassificationID = this.getAllSubClassification(conn, CID);
		if (allSubClassificationID.contains(PID))
			throw new DAORuntimeException("不允許放入在自己的下層分類中",
					UpdateClassificationResponse.STATECODE.UPDATEPARENTFAILURE);
		// 更改PID
		try {
			String SQL = prop.getProperty("modifyParentOfClassification");
			PreparedStatement stat = conn.prepareStatement(SQL);
			stat.setInt(1, PID);
			stat.setInt(2, CID);
			stat.executeUpdate();
			SQL = prop.getProperty("searchParentbyCID");
			stat = conn.prepareStatement(SQL);
			stat.setInt(1, CID);
			ResultSet rs = stat.executeQuery();
			if (rs.next() && (rs.getInt(1) == PID))
				return;
			throw new DAORuntimeException("修改上層分類失敗",
					UpdateClassificationResponse.STATECODE.UPDATEPARENTFAILURE);
		} catch (SQLException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "更新上層分類發生SQLException "));
			throw e;
		}
	}

	/**
	 * 取得所有的子類別清單
	 * 
	 * @param conn 資料庫連接
	 * @param PID  分類ID
	 * @return 取得所有的子類別清單
	 * @throws SQLException
	 */
	private List<Integer> getAllSubClassification(Connection conn, int PID) throws SQLException {
		List<Integer> all = new LinkedList<Integer>();
		List<Integer> sub = this.getSubClassification(conn, PID);
		all.addAll(sub);
		for (Integer subClass : sub) {
			try {
				all.addAll(this.getAllSubClassification(conn, subClass.intValue()));
			} catch (NullPointerException e) {
				// 到底了
			}
		}
		return all;
	}

	/**
	 * 取得下一層子分類ID集合
	 * 
	 * @param conn 資料庫連接
	 * @param PID  分類ID
	 * @return 下一層子分類ID的集合
	 * @throws SQLException
	 */
	private List<Integer> getSubClassification(Connection conn, int PID) throws SQLException {
		try {
			String SQL = prop.getProperty("searchCIDByParent");
			PreparedStatement stat = conn.prepareStatement(SQL);
			stat.setInt(1, PID);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				List<Integer> list = new LinkedList<Integer>();
				do {
					list.add(rs.getInt(1));
				} while (rs.next());
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "取得子分類ID集合，有SQLException問題．"));
			throw e;
		}
		return null;
	}
}
