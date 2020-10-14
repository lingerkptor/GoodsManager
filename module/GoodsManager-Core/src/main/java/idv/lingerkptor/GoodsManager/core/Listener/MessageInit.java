package idv.lingerkptor.GoodsManager.core.Listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.Message.MessageConfig;
import idv.lingerkptor.GoodsManager.core.Message.MessageManager;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;

public class MessageInit implements ServletContextListener {

	private static MessageManager systemMsgManager; 
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("取得系統訊息設定");
		// 取得系統訊息設定
		MessageConfig msgconfig = ConfigReader.getMsgConfig();
		System.out.println("location: "+msgconfig.getLocation());
		systemMsgManager = new MessageManager(msgconfig);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	

}
