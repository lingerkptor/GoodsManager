package idv.GoodsManager.installation;


import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DatabaseConfigImp;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;

/**
 * 客製化設定資料庫
 * 
 * @author lingerkptor
 * 
 */
public class CustomizedDBConfig {
	/**
	 * 建立資料庫設定
	 * 
	 * @param reqContext
	 */
	public static DatabaseConfigImp createDBcofig(idv.GoodsManager.installation.api.request.InstallationRequest reqContext) {

		String dbName = reqContext.getDatabaseName();
		String driver = reqContext.getJDBCName();
		String driverUrl = ConfigReader.getWebAddr() + "/lib";
		String url = reqContext.getURL();
		String account = reqContext.getAccount();
		String password = reqContext.getPassword();
		int maxConnection = reqContext.getMaxConnection();

		DatabaseConfigImp dbconfig = new DatabaseConfigImp(dbName, driver, driverUrl, url, account, password,
				maxConnection);
		if (dbconfig.saveConfig())
			return dbconfig;// System.out.println("設定成功");
		else
			return null; // System.out.println("設定失敗");
	}

}
