package idv.lingerkptor.GoodsManager.core.Listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.util.DBOperator.DBOperatorException;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;

public class DatabaseInit implements ServletContextListener {

	/**
	 * 應用程式啟動時，將資料庫準備好
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			System.out.println("取得資料庫設定");
			// 取得資料庫設定
			DatabaseConfig dbconfig = ConfigReader.getConfigReader().getDBConfig();
			System.out.println("設定資料庫");
			DataAccessCore.setDatabase(dbconfig);
		} catch (DBOperatorException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 應用程式關閉時，將資料庫關閉
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			System.out.println("關閉資料庫");
			DataAccessCore.close();
		} catch (DBOperatorException e) {
			e.printStackTrace();
//			switch (e.getState()) {
//			case UNREADY:// 沒有資料庫可關閉 ，
//				
//			case CLOSING:// 關閉中，
//				
//			case CLOSED:// 已經關閉，
//				
//			default:
//				e.printStackTrace();
//				break;
//			}
		}
	}

}
