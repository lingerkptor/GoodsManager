package idv.lingerkptor.GoodsManager.core.Listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;

/**
 * 資料庫初始化
 * 
 * @author lingerkptor
 *
 */
@WebListener
public class ConfigInit implements ServletContextListener {
	

	/**
	 * 應用程式啟動時，讀取相關設定
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			// 讀取設定
			ConfigReader.readConfig(sce.getServletContext().getRealPath("WEB-INF"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConfigReader.close();
	}

	
}
