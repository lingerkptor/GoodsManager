package idv.lingerkptor.GoodsManager.core.DataAccess;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import idv.lingerkptor.util.DBOperator.ConnectPool;
import idv.lingerkptor.util.DBOperator.DBOperatorException;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;
import idv.lingerkptor.util.DBOperator.Database;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;
import idv.lingerkptor.util.DBOperator.ConnectPool.STATE;

/**
 * 資料庫初始化
 * 
 * @author lingerkptor
 *
 */
@WebListener
public class DataAccessCore implements ServletContextListener {
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

	/**
	 * 應用程式啟動時，ConnectionPool先準備好
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		// DB設定檔建立
		DatabaseConfig config = new DatabaseConfigImp(sce.getServletContext().getRealPath("WEB-INF"));
		try {
			DataAccessCore.pool.setDatabase(Database.getDatabase(config));
		} catch (DBOperatorException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 應用程式關閉時，將ConnectionPool內的資源釋放．
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			DataAccessCore.pool.close();
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
