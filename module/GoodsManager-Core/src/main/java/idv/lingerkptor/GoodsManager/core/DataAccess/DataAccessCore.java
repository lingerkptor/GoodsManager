package idv.lingerkptor.GoodsManager.core.DataAccess;

import java.sql.Connection;
import java.sql.SQLException;

import idv.lingerkptor.util.DBOperator.ConnectPool;
import idv.lingerkptor.util.DBOperator.ConnectPool.STATE;
import idv.lingerkptor.util.DBOperator.DBOperatorException;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;
import idv.lingerkptor.util.DBOperator.Database;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;

public class DataAccessCore {
	/**
	 * 連接池
	 */
	private static ConnectPool pool;
	private static DataAccessTemplate template;

	public static STATE getState() {
		return pool.getState();
	}

	/**
	 * 取得資料處理樣板
	 * 
	 * @return 資料處理樣板
	 */
	public static DataAccessTemplate getSQLTemplate() {
		return template;
	}

	/**
	 * 交付設定檔，設定資料庫．
	 * 
	 * @param dbconfig 資料庫設定
	 */
	public static void setDatabase(DatabaseConfig dbconfig) {
		pool = new ConnectPool();
		System.out.println("設定資料庫");
		pool.setDatabase(Database.getDatabase(dbconfig));
		System.out.println("測試連線");
		testConnection();
		System.out.println("建立資料存取樣板");
		template = new DataAccessTemplate(pool);
	}

	/**
	 * 測試連線
	 */
	public static boolean testConnection() {
		Connection conn = null;
		try {
			conn = pool.getConnection();
			if (conn != null) {
				pool.returnConnection(conn);
				return true;
			}
		} catch (DBOperatorException e) {
			e.printStackTrace();
			pool.close();
		} catch (SQLException e) {
			e.printStackTrace();
			pool.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 關閉資料處理
	 */
	public static void close() {
		pool.close();
	}
}
