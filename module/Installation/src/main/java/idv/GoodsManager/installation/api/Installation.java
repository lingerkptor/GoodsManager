package idv.GoodsManager.installation.api;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.GoodsManager.installation.CustomizedDBConfig;
import idv.GoodsManager.installation.DataAccess.CreateTable;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.DatabaseInit;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ClassName;
import idv.lingerkptor.GoodsManager.core.annotation.Method;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.util.DBOperator.DatabaseConfig;

@WebServlet("/api/install")
public class Installation extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = 47239711249208659L;
	private Connection conn = null;

	/**
	 * 
	 */
	@Override
	@Method(value = "POST") // 這一行一定要
	@ClassName(value = "idv.GoodsManager.installation.api.Installation$RequestContext") // 這一行一定要
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		RequestContext reqContext = (RequestContext) req.getAttribute("obj");
		if (reqContext.isCustomized())
			// 建立自訂資料庫設定
			CustomizedDBConfig.createDBcofig(reqContext);

		// 測試連線
		try {
			if (DataAccessCore.testConnection(ConfigReader.getDBConfig())) {
				System.out.println("連線成功");// 連線OK
				MessageInit.getMsgManager().deliverMessage( // 廣播通知連線成功
						new Message(Message.Category.info, "連線測試成功．"));
			}
			else {
				System.out.println("連線失敗"); // 連線失敗
				MessageInit.getMsgManager().deliverMessage( // 廣播通知連線失敗
						new Message(Message.Category.info, "連線測試失敗，請檢查自定義資料庫設定．"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 建立資料表
		CreateTable createTable = new CreateTable();
		
	}

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
	public class RequestContext {
		private boolean customized = false;// 是否客製化資料庫
		private String databaseName = null;// 資料庫名稱
		private String JDBCName = null;// JDBC名稱
		private String URL = null;// 資料庫URL
		private String account = "";// 資料庫帳號
		private String password = "";// 資料庫密碼
		private int maxConnection = 1;// 最大連線數

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

	}

}
