package idv.lingerkptor.GoodsManager.core.Listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;

/**
 * 資料庫初始化
 * 
 * @author lingerkptor
 *
 */
public class ConfigInit implements ServletContextListener {

	/**
	 * 應用程式啟動時，讀取相關設定
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("讀取設定檔");
		// 讀取設定
		ConfigReader.configReaderBuilder(sce.getServletContext().getRealPath("WEB-INF"))
				.loadConfig();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConfigReader.close();
	}

}
