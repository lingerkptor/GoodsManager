package idv.GoodsManager.installation.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;
/**
 * 插入資料
 * @author lingerkptor
 * 
 */
public class AddDataSQL implements PreparedStatementCreator {
	Map<String, Integer> datas = new HashMap<String, Integer>();

	public void addData(String column1, int column2) {
		datas.put(column1, column2);
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) {
		String SQL = "Insert into Test Values (?,?)";
		PreparedStatement preps = null;
		try {
			if (!datas.isEmpty()) {
				preps = conn.prepareStatement(SQL);

				for (String key : datas.keySet()) {
					preps.setString(1, key);
					preps.setInt(2, datas.get(key));
					preps.addBatch();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preps;
	}

}
