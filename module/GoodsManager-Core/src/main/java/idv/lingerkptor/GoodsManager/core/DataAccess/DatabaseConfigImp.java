package idv.lingerkptor.GoodsManager.core.DataAccess;



import java.net.URI;

import idv.lingerkptor.util.DBOperator.DatabaseConfig;

public class DatabaseConfigImp implements DatabaseConfig {

	private String driver;
	private String driverUrl;
	private String url;
	private String account;
	private String password;
	private int maxConnection = 1;
	private URI sqlUri;

	@SuppressWarnings("unused")
	private DatabaseConfigImp() {

	}

	public DatabaseConfigImp(String driver, String driverUrl, String url, String account, String password,
			int maxConnection,URI sqlUri) {
		this.driver = driver;
		this.driverUrl = driverUrl;
		this.url = url;
		this.account = account;
		this.password = password;
		this.maxConnection =maxConnection;
		this.sqlUri=sqlUri;
	}

	@Override
	public String getDriver() {
		return driver;
	}

	@Override
	public String getDriverUrl() {
		return driverUrl;
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

	public URI getSqlUri() {
		return sqlUri;
	}

}
