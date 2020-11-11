package idv.GoodsManager.installation.api.request;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

/**
 * json 資料<br/>
 * { <br/>
 * customized:boolean, 是否客製化資料庫 <br/>
 * databaseName: String 資料庫名稱 <br/>
 * JDBC:String JDBC名稱 <br/>
 * URL:String 資料庫URL<br/>
 * account: String 資料庫帳號<br/>
 * password:String 資料庫密碼 <br/>
 * maxConnection:Int 最大連線數 <br/>
 * }
 */
public class InstallationRequest implements Request {
	private boolean customized = false;// 是否客製化資料庫
	private String databaseName = null;// 資料庫名稱
	private String JDBCName = null;// JDBC名稱
	private String URL = null;// 資料庫URL
	private String account = "";// 資料庫帳號
	private String password = "";// 資料庫密碼
	private int maxConnection = 1;// 最大連線數

	public InstallationRequest() {

	}

	public boolean isCustomized() {
		return customized;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public String getJDBCName() {
		return JDBCName;
	}

	public String getURL() {
		return URL;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public int getMaxConnection() {
		return maxConnection;
	}

	@Override
	public void setAttribute(HttpSession session) {
	}


}