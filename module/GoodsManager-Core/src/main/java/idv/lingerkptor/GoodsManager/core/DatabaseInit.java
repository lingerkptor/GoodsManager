package idv.lingerkptor.GoodsManager.core;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import idv.lingerkptor.util.DBOperator.ConnectPool;
import idv.lingerkptor.util.DBOperator.Database;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;

@WebListener
public class DatabaseInit implements ServletContextListener {
	static {
		File file = new File(".");
		try {
			throw new Exception("test" + file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("test:   " + file.getPath());

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// DB設定檔建立
		DatabaseConfig config = new DatabaseConfigImp(sce.getServletContext().getRealPath("WEB-INF"));
		// 將設定檔餵給DB，如果沒有餵，在ConnectPool內會拋出Exception
		Database.setDatabaseConfig(config);
		try {
			ConnectPool.setDatabase(Database.getDatabase());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
