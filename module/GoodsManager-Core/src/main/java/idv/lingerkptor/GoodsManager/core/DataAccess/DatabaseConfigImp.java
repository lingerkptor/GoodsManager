package idv.lingerkptor.GoodsManager.core.DataAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import idv.lingerkptor.util.DBOperator.DatabaseConfig;

public class DatabaseConfigImp implements DatabaseConfig {

	private String driver;
	private String driverUrl;
	private String url;
	private String account;
	private String password;
	private int maxConnection = 1;
	private String sqlURL;
	private String dbName;

	@SuppressWarnings("unused")
	private DatabaseConfigImp() {

	}

	public DatabaseConfigImp(String dbName, String driver, String driverUrl, String url,
			String account, String password, int maxConnection) {
		this.dbName = dbName;
		this.driver = driver;
		this.driverUrl = driverUrl;
		this.url = url;
		this.account = account;
		this.password = password;
		this.maxConnection = maxConnection;
		this.sqlURL = ConfigReader.getConfigReader().getWebAddr() + "/sql/" + dbName;
	}

	public boolean saveConfig() throws NullPointerException {
		Properties prop = new Properties();
		File dbConfig = new File(ConfigReader.getConfigReader().getWebAddr() + "/config/db."
				+ dbName + ".properties");
		FileReader reader = null;
		FileWriter writer = null;
		try {
			writer = new FileWriter(dbConfig);
			reader = new FileReader(dbConfig);
			prop.load(reader);
			prop.put("name", this.dbName);
			prop.put("driver", this.driver);
			prop.put("driverUrl", this.driverUrl);
			prop.put("url", this.url);
			prop.put("maxConnection", String.valueOf(this.maxConnection));
			prop.put("account", this.account);
			prop.put("password", this.password);
			prop.store(writer, this.dbName);
		} catch (FileNotFoundException e) {
			try {
				dbConfig.createNewFile();
				this.saveConfig();
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	@Override
	public String getDriver() {
		return driver;
	}

	@Override
	public String getDriverUrl() {
		return ConfigReader.getConfigReader().getWebAddr() + driverUrl;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getAccount() {
		return account;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public int getMaxConnection() {
		return maxConnection;
	}

	/**
	 * 取得SQL路徑
	 * 
	 * @return the sqlURL
	 */
	public String getSqlURL() {
		return sqlURL;
	}

	/**
	 * 取得資料庫名稱
	 * 
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

}
