package idv.lingerkptor.GoodsManager.core.DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.core.Message.MessageConfig;

public class ConfigReader {
	private static String webAddr;
	private static Properties props = new Properties();
	private static Properties dbprops = new Properties();
	private static DatabaseConfigImp dbconfig;
	private static MessageConfig msgConfig;

	private ConfigReader() {
	}

	/**
	 * 讀取設定
	 * 
	 * @param addr
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void readConfig(String addr) throws FileNotFoundException, IOException {
		webAddr = addr;
		updateConfig();
	}

	/**
	 * 重載設定 必須要先執行過一次readConfig(String addr)
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void updateConfig() throws FileNotFoundException, IOException {
		// 全域設定
		System.out.println("讀取全域設定檔");
		props.load(new FileInputStream(new File(webAddr + "/config/config.properties")));
		// 讀取系統訊息設定
		System.out.println("讀取系統訊息設定檔");
		ConfigReader.readMessageConfig();
		// 讀取資料庫設定
		System.out.println("讀取資料庫設定檔");
		ConfigReader.readDatabaseConfig();
	}

	/**
	 * 讀取系統訊息設定
	 */
	private static void readMessageConfig() {
		msgConfig = new MessageConfig(props.getProperty("msgURL"));
	}

	/**
	 * 讀取資料庫設定
	 **/
	private static void readDatabaseConfig() throws IOException {
		// 資料庫設定
		try {
			dbprops.load(new FileInputStream(new File(webAddr + "/config/" + props.getProperty("dbconfig"))));
		} catch (FileNotFoundException e) {
			System.err.println("dbconfig 遺失");
			throw e;
		} catch (IOException e) {
			System.err.println("資料庫可能還沒建立，請先確定資料庫的URL，");
			throw e;
		}
		// 建立資料庫設定
		dbconfig = new DatabaseConfigImp(dbprops.getProperty("name"),dbprops.getProperty("driver"), webAddr + dbprops.getProperty("driverUrl"),
				dbprops.getProperty("url"), dbprops.getProperty("account"), dbprops.getProperty("password"),
				Integer.parseInt(dbprops.getProperty("maxConnection")));
	}

	/**
	 * 取得資料庫設定
	 * 
	 * @return
	 * @throws IOException
	 */
	public static DatabaseConfigImp getDBConfig() throws IOException {
		return dbconfig;
	}

	public static void close() {
		// 原本想要關閉Properties,結果好像不需要處理.

	}

	/**
	 * 取得訊息設定
	 * 
	 * @return 訊息設定
	 */
	public static MessageConfig getMsgConfig() {
		return msgConfig;
	}

	public static String getWebAddr() {
		return webAddr;
	}

}
