package idv.lingerkptor.GoodsManager.Operator.DataAccess;

import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.bean.Classification;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;
import idv.lingerkptor.util.DBOperator.RowCallbackHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GetClassificationListDAO implements PreparedStatementCreator, RowCallbackHandler {

	private Properties prop;
	private Map<Integer, Classification> temp = new HashMap<Integer, Classification>();

	@SuppressWarnings("unused")
	private GetClassificationListDAO() {
	}

	public GetClassificationListDAO(Properties prop) {
		this.prop = prop;
	}

	public List<Classification> getClassList() {
		List<Classification> classes = new LinkedList<Classification>();
		temp.values().forEach((element) -> {
			if (element.getPID() != null) {
				try {
					temp.get(element.getPID()).addSubClassification(element);
				} catch (NullPointerException e) {
					MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
							"資料出錯" + element.getClassificationName() + "上層分類找不到"));
					classes.add(element);
				}
			} else
				classes.add(element);
		});
		return classes;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String SQL = prop.getProperty("listAllClassification");
		PreparedStatement stat = conn.prepareStatement(SQL);
		stat.addBatch();
		stat.execute();
		return stat;
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		Classification classElement = new Classification(rs.getInt(1), // CID
				rs.getString(2), // Classification Name
				rs.getInt(3)); // Parent Classification ID
		temp.put(classElement.getCID(), classElement);
	}

}
