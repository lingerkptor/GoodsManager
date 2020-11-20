package idv.lingerkptor.GoodsManager.core.Listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.Message.MessageConfig;
import idv.lingerkptor.GoodsManager.core.Message.MessageManager;

public class MessageInit implements ServletContextListener {

	private static MessageManager msgManager = null;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 取得訊息設定
		System.out.println("取得訊息設定");
		MessageConfig msgconfig = ConfigReader.getConfigReader().getMsgConfig();
		// 反序列化
		File msgManagerEntity = new File(
				ConfigReader.getConfigReader().getWebAddr() + "/Entity/MessageManager.json");

		try {
			Gson gson = new Gson();
			msgManager = gson.fromJson(new FileReader(msgManagerEntity), MessageManager.class);
			if (msgManager == null) {
				msgManagerEntity.delete();
				throw new FileNotFoundException("內容錯誤，重新建立MessageManager");
			}
			msgManager.setConfig(msgconfig);
		} catch (JsonSyntaxException e) {
			System.err.println("MessageManager 反序列化失敗，請重新啟動");
			msgManagerEntity.delete();
			e.printStackTrace();
		} catch (JsonIOException e) {
			System.err.println("MessageManager 反序列化失敗，請重新啟動");
			msgManagerEntity.delete();
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			new File(ConfigReader.getConfigReader().getWebAddr() + "/Entity/").mkdirs();
			msgManager = new MessageManager(msgconfig);
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		File msgManagerEntity = new File(ConfigReader.getConfigReader().getWebAddr() + "/Entity/MessageManager.json");
		if (msgManagerEntity.exists())
			msgManagerEntity.delete();
		try {
			msgManagerEntity.createNewFile();

			Gson gson = new Gson();
			Writer fileWriter = new FileWriter(msgManagerEntity);
			gson.toJson(msgManager, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			System.err.println("Message Manager 序列化失敗");
			e.printStackTrace();
		}

	}

	public static MessageManager getMsgManager() {
		return msgManager;
	}

}
