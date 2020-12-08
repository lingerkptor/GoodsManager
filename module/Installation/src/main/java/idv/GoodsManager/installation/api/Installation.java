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
import idv.GoodsManager.installation.api.response.InstallResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.DataAccess.DatabaseConfigImp;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.RequestType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.ResponseType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/install")
public class Installation extends Service {

	private static final long serialVersionUID = 47239711249208659L;

	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponseType.Json) // 請求跟回應的標頭型態
	public Response process(Request reqObj) {
		InstallationRequest reqContext = (InstallationRequest) reqObj;

		/**
		 * STEP1 建立自訂資料庫設定(如果有需要)
		 */
		DatabaseConfigImp dbconfig = ConfigReader.getConfigReader().getDBConfig();
		// 如果要自訂資料庫，需要預先上傳資料
		if (reqContext.isCustomized()) {
			if ((dbconfig = CustomizedDBConfig.createDBconfig(reqContext)) == null) {// 建立自訂資料庫設定
				MessageInit.getMsgManager().deliverMessage( //
						new Message(Message.Category.warn, "建立自訂資料庫設定失敗．"));
				// 回傳安裝失敗
				return InstallResponse.buildDBConfigFault();
			} else
				DataAccessCore.setDatabase(dbconfig);
		} else
			MessageInit.getMsgManager().deliverMessage( //
					new Message(Message.Category.info, "初始化預設資料庫"));

		/**
		 * STEP2 測試連線 start
		 */
		if (DataAccessCore.testConnection()) {
			System.out.println("連線成功");// 連線OK
			MessageInit.getMsgManager().deliverMessage( // 廣播通知連線成功
					new Message(Message.Category.info, "連線測試成功．"));
		} else {
			System.out.println("連線失敗"); // 連線失敗
			MessageInit.getMsgManager().deliverMessage( // 廣播通知連線失敗
					new Message(Message.Category.warn, "連線測試失敗，請檢查自定義資料庫設定．"));
			// 回傳連線失敗
			return InstallResponse.testConnectFault();
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
			return InstallResponse.readSQLFileFault();
		} catch (IOException e) {
			MessageInit.getMsgManager().deliverMessage( // 廣播通知建立資料表失敗訊息
					new Message(Message.Category.warn, // 訊息種類
							"讀寫檔案出錯，請確認檔案狀態，如：檔案權限．Message:  " + e.getMessage()));
			return InstallResponse.readSQLFileFault();
		}

		/**
		 * STEP4 建立資料表
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
					new Message(Message.Category.warn, "SQL發生錯誤，訊息：　 " + e.getMessage()));
			// 回傳建立資料表失敗
			return InstallResponse.createTableFault(InstallResponse.ERRORCODE.SQLERROR.name());
		} catch (DAORuntimeException e) {
			return InstallResponse.createTableFault(e.getCode());
		}
		// 建立資料表END

		/**
		 * STEP5. 匯入主設定檔內(預設的不用)
		 */
		if (reqContext.isCustomized())
			try {
				ConfigReader.getConfigReader().setNewDBConfig(reqContext.getDatabaseName());
			} catch (NullPointerException e) {
				MessageInit.getMsgManager()
						.deliverMessage(new Message(Message.Category.err, e.getMessage()));
				e.printStackTrace();
				return InstallResponse.importMainConfigFault();
			}

		/**
		 * 完成
		 */
		MessageInit.getMsgManager().deliverMessage( // 廣播通知安裝成功
				new Message(Message.Category.info, "安裝成功."));
		return InstallResponse.createTableSucess();
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
