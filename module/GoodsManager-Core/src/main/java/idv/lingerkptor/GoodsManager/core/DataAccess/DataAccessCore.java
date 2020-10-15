package idv.lingerkptor.GoodsManager.core.DataAccess;

import idv.lingerkptor.util.DBOperator.ConnectPool;
import idv.lingerkptor.util.DBOperator.ConnectPool.STATE;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;
import idv.lingerkptor.util.DBOperator.Database;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;

public class DataAccessCore {
	/**
	 * 連接池
	 */
	private static ConnectPool pool = new ConnectPool();
	private static DataAccessTemplate template;

	public static STATE getState() {
		return pool.getState();
	}
	/**
	 * 取得資料處理樣板
	 * 
	 * @return 資料處理樣板
	 */
	public static  DataAccessTemplate getSQLTemplate() {
		System.out.println("取得資料存取樣板");
		return template;
	}

	/**
	 * 交付設定檔，設定資料庫．
	 * 
	 * @param dbconfig 資料庫設定
	 */
	public static void setDatabase(DatabaseConfig dbconfig) {
		System.out.println("設定資料庫");
		pool.setDatabase(Database.getDatabase(dbconfig));
		System.out.println("建立資料存取樣板");
		template = new DataAccessTemplate(pool);
	}

	/**
	 * 關閉資料處理
	 */
	public static void close() {
		pool.close();
	}
}
