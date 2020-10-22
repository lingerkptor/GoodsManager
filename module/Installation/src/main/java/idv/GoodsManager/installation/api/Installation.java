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

	/**
	 * STEP1 建立自訂資料庫設定(如果有需要) STEP2 測試連線
	 */
	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponceType.Json) // 請求跟回應的標頭型態
	public Responce process(Request reqObj) {
		InstallationRequest reqContext = (InstallationRequest) reqObj;

		DatabaseConfigImp dbconfig = ConfigReader.getDBConfig();
		// 如果要自訂資料庫，需要預先上傳資料
		if (reqContext.isCustomized())
			if ((dbconfig = CustomizedDBConfig.createDBcofig(reqContext)) == null) {// 建立自訂資料庫設定
				MessageInit.getMsgManager().deliverMessage( //
						new Message(Message.Category.warn, "建立自訂資料庫設定失敗．"));
				// 回傳安裝失敗
				return (InstallResponce) idv.GoodsManager.installation.api.responce.InstallResponce
						.buildDBConfigFault();
			} else {
				DataAccessCore.setDatabase(dbconfig);
			}

		// 測試連線 start
		if (DataAccessCore.testConnection(ConfigReader.getDBConfig())) {
			System.out.println("連線成功");// 連線OK
			MessageInit.getMsgManager().deliverMessage( // 廣播通知連線成功
					new Message(Message.Category.info, "連線測試成功．"));
		} else {
			System.out.println("連線失敗"); // 連線失敗
			MessageInit.getMsgManager().deliverMessage( // 廣播通知連線失敗
					new Message(Message.Category.info, "連線測試失敗，請檢查自定義資料庫設定．"));
			// 回傳連線失敗
			return (InstallResponce) idv.GoodsManager.installation.api.responce.InstallResponce.testConnectFault();
		} // 測試連線 end

		// 取得CreateTable所需要的SQL(放在properties裡面)
		Properties createTableSqlMap = new Properties();
		try {
			createTableSqlMap.load(
					new FileInputStream(new File(ConfigReader.getDBConfig().getSqlUri() + "/CreateTable.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 建立資料表
		CreateTable createTable = new CreateTable(createTableSqlMap);

		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		try {
			template.update(createTable);
		} catch (SQLException e) {
			MessageInit.getMsgManager().deliverMessage( // 廣播通知連線失敗
					new Message(Message.Category.info, "SQL發生錯誤，請詳閱訊息，並確認SQL內容．Message:  " + e.getMessage()));
			// 回傳建立資料表失敗
			return (InstallResponce) idv.GoodsManager.installation.api.responce.InstallResponce.createTableFault();
		}
		// 建立資料表END

		MessageInit.getMsgManager().deliverMessage( // 廣播通知連線失敗
				new Message(Message.Category.info, "安裝成功."));
		return (InstallResponce) idv.GoodsManager.installation.api.responce.InstallResponce.createTableSucess();
	}

	@Override
	protected void configRequestClass() {
		this.requestObj = InstallationRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.operater(req, resp);
	}

}
