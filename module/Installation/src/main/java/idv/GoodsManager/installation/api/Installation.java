package idv.GoodsManager.installation.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.GoodsManager.installation.CustomizedDBConfig;
import idv.GoodsManager.installation.DataAccess.CreateTable;
import idv.GoodsManager.installation.api.request.InstallationRequest;
import idv.GoodsManager.installation.api.responce.InstallResponce;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.DataAccess.DatabaseConfigImp;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.RequestType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.ResponceType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.responce.Responce;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/install")
public class Installation extends Service {

	private static final long serialVersionUID = 47239711249208659L;

	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponceType.Json) // 請求跟回應的標頭型態
	public Responce process(Request reqObj) {
		InstallationRequest reqContext = (InstallationRequest) reqObj;
		/**
		 * STEP1 建立自訂資料庫設定(如果有需要)
		 */
		DatabaseConfigImp dbconfig = ConfigReader.getConfigReader().getDBConfig();
		// 如果要自訂資料庫，需要預先上傳資料
		if (reqContext.isCustomized())
			if ((dbconfig = CustomizedDBConfig.createDBconfig(reqContext)) == null) {// 建立自訂資料庫設定
				MessageInit.getMsgManager().deliverMessage( //
						new Message(Message.Category.warn, "建立自訂資料庫設定失敗．"));
				// 回傳安裝失敗
				return InstallResponce.buildDBConfigFault();
			} else {
				DataAccessCore.setDatabase(dbconfig);
			}

		/**
		 * STEP2 測試連線 start
		 */
		if (DataAccessCore.testConnection(ConfigReader.getConfigReader().getDBConfig())) {
			System.out.println("連線成功");// 連線OK
			MessageInit.getMsgManager().deliverMessage( // 廣播通知連線成功
					new Message(Message.Category.info, "連線測試成功．"));
		} else {
			System.out.println("連線失敗"); // 連線失敗
			MessageInit.getMsgManager().deliverMessage( // 廣播通知連線失敗
					new Message(Message.Category.warn, "連線測試失敗，請檢查自定義資料庫設定．"));
			// 回傳連線失敗
			return InstallResponce.testConnectFault();
		} // 測試連線 end

		/**
		 * STEP3 取得CreateTable所需要的SQL(放在properties裡面)
		 */
		Properties createTableSqlMap = new Properties();
		try {
			createTableSqlMap.load(new FileInputStream(
					new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/CreateTable.properties")));
		} catch (FileNotFoundException e) {
			MessageInit.getMsgManager().deliverMessage( // 廣播通知建立資料表失敗訊息
					new Message(Message.Category.warn, // 訊息種類
							"沒有找到CreateTable.properties．Message:  " + e.getMessage()));
			return InstallResponce.createTableFault();
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage( // 廣播通知建立資料表失敗訊息
					new Message(Message.Category.warn, // 訊息種類
							"讀寫檔案出錯，請確認檔案狀態，如：檔案權限．Message:  " + e.getMessage()));
			return InstallResponce.createTableFault();
		}

		/**
		 * STEP4 建立資料表
		 * 
		 */
		CreateTable createTable = new CreateTable(createTableSqlMap);

		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		try {
			System.out.println("開始建立資料表");
			template.update(createTable);
			System.out.println("建立資料表成功");
		} catch (SQLException e) {
			System.out.println("建立資料表失敗");
			MessageInit.getMsgManager().deliverMessage( // 廣播通知建立資料表失敗訊息
					new Message(Message.Category.info, "SQL發生錯誤，請詳閱訊息，並確認SQL內容． "));
			MessageInit.getMsgManager().deliverMessage( // 廣播通知建立資料表失敗訊息
					new Message(Message.Category.warn,
							"SQL發生錯誤，請詳閱訊息，並確認SQL內容．Message:  " + e.getMessage()));
			// 回傳建立資料表失敗
			return InstallResponce.createTableFault();
		} // 建立資料表END

		/**
		 * STEP5. 匯入主設定檔內
		 */
		try {
			ConfigReader.getConfigReader().setNewDBConfig(reqContext.getDatabaseName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err,
					"找不到db." + reqContext.getDatabaseName() + ".properties檔案"));
			return InstallResponce.createTableFault();
		} catch (NullPointerException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			e.printStackTrace();
			return InstallResponce.createTableFault();
		} catch (IOException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "設定讀取異常."));
			e.printStackTrace();
			return InstallResponce.createTableFault();
		}

		/**
		 * 完成
		 */
		MessageInit.getMsgManager().deliverMessage( // 廣播通知安裝成功
				new Message(Message.Category.info, "安裝成功."));
		return InstallResponce.createTableSucess();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = InstallationRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
