package idv.lingerkptor.GoodsManager.core.Listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.Message.MessageConfig;
import idv.lingerkptor.GoodsManager.core.Message.MessageManager;

public class MessageInit implements ServletContextListener {

	private static MessageManager msgManager = null;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("取得訊息設定");
		// 取得訊息設定
		MessageConfig msgconfig = ConfigReader.getMsgConfig();
//		System.out.println("location: " + msgconfig.getLocation());// 查詢訊息存放位址
		msgManager = new MessageManager(msgconfig);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	public static MessageManager getMsgManager() {
		return msgManager;
	}

}
