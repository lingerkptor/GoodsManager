package idv.lingerkptor.GoodsManager.core.Listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.PictureManager;

public class PhotoManagerInit implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("圖片管理初始化 開始");
		PictureManager manager = PictureManager.getPictureManager();
		System.out.println("註冊圖片倉庫");
		manager.registerPWH(ConfigReader.getConfigReader().getWHClass(),
				ConfigReader.getConfigReader().getPhotoConfig());
		System.out.println("圖片管理初始化 結束");
	}

}
