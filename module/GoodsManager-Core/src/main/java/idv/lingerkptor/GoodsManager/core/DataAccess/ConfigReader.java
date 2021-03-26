package idv.lingerkptor.GoodsManager.core.DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
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
	private String photoConfig;
	private String WHClass;
	private String locationListFileName;
	private String locationLibPath;

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
		// 讀取圖片資料夾設定
		System.out.println("讀取圖片資料夾設定");
		this.readPhotoDirConfig();
		// 讀取資料庫設定
		System.out.println("讀取資料庫設定檔");
		this.readDatabaseConfig();
		// 讀取發佈地設定
		System.out.println("讀取發佈地設定");
		this.readLocationConfig();
	}

	/**
	 * 讀取發佈地設定
	 */
	private void readLocationConfig() {
		this.locationListFileName = props.getProperty("systemDir") + "\\"
				+ props.getProperty("locationConfig");
		this.locationLibPath = props.getProperty("systemDir") + "\\"
				+ props.getProperty("locationDir");
	}

	/**
	 * 設定新資料庫設定檔
	 *
	 * @param dbName 資料庫名稱
	 */
	public void setNewDBConfig(String dbName) {
		props.setProperty("dbconfig", "db." + dbName + ".properties");
		this.loadConfig();
	}

	/**
	 * 讀取系統訊息設定
	 */
	private void readMessageConfig() {
		msgConfig = new MessageConfig(
				props.getProperty("systemDir") + "\\" + props.getProperty("msgURL"));
	}

	/**
	 * 讀取暫存資料夾設定
	 */
	private void readTempDirConfig() {
		tempDir = props.getProperty("systemDir") + "\\" + props.getProperty("tempDir");
	}

	/**
	 * 讀取圖片資料夾設定
	 */
	private void readPhotoDirConfig() {
		this.WHClass = props.getProperty("photoWarehouse");
		this.photoConfig = webAddr + "\\config\\" + props.getProperty("photoConfig");
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
				dbprops.getProperty("driverUrl"), dbprops.getProperty("url"),
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

	public String getPhotoConfig() {
		return this.photoConfig;
	}

	public String getWHClass() {
		return this.WHClass;
	}

	/**
	 * @return 發佈地清單檔案名稱
	 */
	public String getLocationListFileName() {
		return this.locationListFileName;
	}

	/**
	 * @return 發布地函式庫路徑
	 */
	public String getLocationLibPath() {
		return this.locationLibPath;
	}

	/**
	 * 回傳指定的設定檔
	 * @throws FileNotFoundException 如果設定檔案不存在 
	 * 
	 */
	public File getOtherConfig(String configName) throws FileNotFoundException {
		File file =new File(this.props.getProperty(configName));
		if(!file.exists()) throw new FileNotFoundException(configName+" is not exist.");
		return file;
	}
}
