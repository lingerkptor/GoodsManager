package idv.lingerkptor.GoodsManager.core.DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import idv.lingerkptor.GoodsManager.core.Message.MessageConfig;

public class ConfigReader {
	private static ConfigReader mainConfig = null;
	private String webAddr;
	private Properties props = new Properties();
	private Properties dbprops = new Properties();
	private DatabaseConfigImp dbconfig;
	private MessageConfig msgConfig;
	private String tempDir;

	private ConfigReader() {
	}

	/**
	 * 建立ConfigReader
	 * 
	 * @param addr 設定放置資源的資料夾位址
	 * @return 取得已建立的ConfigReader
	 */
	public static ConfigReader configReaderBuilder(String addr) {
		if (ConfigReader.mainConfig == null)
			mainConfig = new ConfigReader();
		mainConfig.webAddr = addr;
		return mainConfig;
	}

	/**
	 * 取得已建立的ConfigReader
	 * 
	 * @return 取得已建立的ConfigReader
	 * @throws NullPointerException
	 */
	public static ConfigReader getConfigReader() throws NullPointerException {
		if (ConfigReader.mainConfig == null)
			throw new NullPointerException("The config has not been builded.");
		return mainConfig;
	}

	/**
	 * 重載設定 必須要先執行過一次readConfig(String addr)
	 */
	public void loadConfig() {
		try {
			// 全域設定
			System.out.println("讀取全域設定檔");
			props.load(new FileInputStream(new File(webAddr + "/config/config.properties")));
		} catch (FileNotFoundException e) {
			System.err.println("全域設定檔遺失");
			// 設定檔遺失
		} catch (IOException e) {
			System.err.println("全域設定的IOException");
		}
		// 讀取系統訊息設定
		System.out.println("讀取系統訊息設定檔");
		this.readMessageConfig();
		// 讀取暫存資料夾設定
		System.out.println("讀取暫存資料夾設定");
		this.readTempDirConfig();
		// 讀取資料庫設定
		System.out.println("讀取資料庫設定檔");
		this.readDatabaseConfig();
	}

	/**
	 * 設定新資料庫設定檔
	 * 
	 * @param newDBConfigFileName 新資料庫設定檔檔名
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setNewDBConfig(String newDBConfigFileName)
			throws FileNotFoundException, IOException {
		try {
			dbprops.load(new FileInputStream(new File(webAddr + "/config/" + newDBConfigFileName)));
			props.setProperty("dbconfig", newDBConfigFileName);
			dbprops.store(
					new FileOutputStream(new File(webAddr + "/config/" + newDBConfigFileName)),
					"newDBConfigFileName");
			this.loadConfig();
		} catch (FileNotFoundException e) {
			System.err.println("找不到" + newDBConfigFileName + "檔案");
			throw e;
		} catch (IOException e) {
			System.err.println("設定讀取異常.");
			throw e;
		}
	}

	/**
	 * 讀取系統訊息設定
	 */
	private void readMessageConfig() {
		msgConfig = new MessageConfig(props.getProperty("msgURL"));
	}

	/**
	 * 讀取暫存資料夾設定
	 */
	private void readTempDirConfig() {
		tempDir = props.getProperty("tempDir");
	}

	/**
	 * 讀取資料庫設定
	 **/
	private void readDatabaseConfig() {
		// 資料庫設定
		try {
			try {
				dbprops.load(new FileInputStream(
						new File(webAddr + "/config/" + props.getProperty("dbconfig"))));
			} catch (FileNotFoundException e) {
				System.err.println(props.getProperty("dbconfig") + " 遺失.");
				System.out.println(" 載入預設的dbconfig.");
				try {
					dbprops.load(new FileInputStream(
							new File(webAddr + "/config/db.default.properties")));
				} catch (FileNotFoundException e1) {
					System.err.println(" 預設的dbconfig遺失.");
					e1.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.err.println("資料庫設定讀取異常.");
			e.printStackTrace();
		}
		// 建立資料庫設定
		dbconfig = new DatabaseConfigImp(dbprops.getProperty("name"), dbprops.getProperty("driver"),
				webAddr + dbprops.getProperty("driverUrl"), dbprops.getProperty("url"),
				dbprops.getProperty("account"), dbprops.getProperty("password"),
				Integer.parseInt(dbprops.getProperty("maxConnection")));
	}

	/**
	 * 取得資料庫設定
	 * 
	 * @return
	 */
	public DatabaseConfigImp getDBConfig() {
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
	public MessageConfig getMsgConfig() {
		return msgConfig;
	}

	public String getWebAddr() {
		return webAddr;
	}

	public String getTempDir() {
		return tempDir;
	}

}
