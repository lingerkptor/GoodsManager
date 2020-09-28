package idv.lingerkptor.GoodsManager.installation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import idv.lingerkptor.util.DBOperator.DatabaseConfig;

public class DatabaseConfigImp implements DatabaseConfig {
	private Properties dbprops;
	private String driver;
	private String webAddr;
	private String driverUrl;
	private String url;
	private String account;
	private String password;
	private int maxConnection;

	@SuppressWarnings("unused")
	private DatabaseConfigImp() {
	}

	public DatabaseConfigImp(String webAddr) {
		this.webAddr = webAddr;
		File propsfile = new File(webAddr + "/config/db.properties");
		if (!propsfile.exists())
			propsfile = new File(webAddr + "/config/db.default.properties");
		dbprops = new Properties();
		try {
			this.dbprops.load(new FileInputStream(propsfile));
			this.account = dbprops.getProperty("account");
			this.driver = this.dbprops.getProperty("driver");
			this.driverUrl = this.dbprops.getProperty("driverUrl");
			this.password = this.dbprops.getProperty("password");
			this.url = this.dbprops.getProperty("url");
			this.maxConnection = Integer.parseInt(this.dbprops.getProperty("maxConnection"));
		} catch (FileNotFoundException e) {
			System.err.println("沒找到檔案");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getAccount() {
		return this.account;
	}

	@Override
	public String getDriver() {
		return this.driver;
	}

	@Override
	public String getDriverUrl() {
		return this.webAddr + this.driverUrl;
	}

	@Override
	public int getMaxConnection() {
		return this.maxConnection;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

}
