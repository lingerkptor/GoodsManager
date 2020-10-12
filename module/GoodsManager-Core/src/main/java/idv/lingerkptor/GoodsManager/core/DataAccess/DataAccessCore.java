package idv.lingerkptor.GoodsManager.core.DataAccess;

import idv.lingerkptor.util.DBOperator.ConnectPool;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;
import idv.lingerkptor.util.DBOperator.Database;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;

public class DataAccessCore {
	/**
	 * 連接池
	 */
	private static ConnectPool pool = new ConnectPool();

	/**
	 * 取得資料處理樣板
	 * 
	 * @return 資料處理樣板
	 */
	public static DataAccessTemplate getSQLTemplate() {
		return new DataAccessTemplate(pool);
	}

	public static void setDatabase(DatabaseConfig dbconfig) {
		pool.setDatabase(Database.getDatabase(dbconfig));
	}

	public static void close() {
		pool.close();
	}
}
