package idv.GoodsManager.installation.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.GoodsManager.installation.CustomizedDBConfig;
import idv.GoodsManager.installation.DataAccess.CreateTable;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.RequestType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.ResponceType;
import idv.lingerkptor.GoodsManager.core.api.Service;

@WebServlet("/api/install")
public class Installation<InstallationRequest, Object> extends Service<InstallationRequest, Object> {

	private static final long serialVersionUID = 47239711249208659L;

	/**
	 * 
	 */
	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponceType.Json) // 請求跟回應的標頭型態
	public Object process(InstallationRequest reqObj) {
		idv.GoodsManager.installation.api.request.InstallationRequest reqContext = (idv.GoodsManager.installation.api.request.InstallationRequest) reqObj;
		if (reqContext.isCustomized())
			// 建立自訂資料庫設定
			CustomizedDBConfig.createDBcofig(reqContext);

		// 測試連線
		try {
			if (DataAccessCore.testConnection(ConfigReader.getDBConfig())) {
				System.out.println("連線成功");// 連線OK
				MessageInit.getMsgManager().deliverMessage( // 廣播通知連線成功
						new Message(Message.Category.info, "連線測試成功．"));
			} else {
				System.out.println("連線失敗"); // 連線失敗
				MessageInit.getMsgManager().deliverMessage( // 廣播通知連線失敗
						new Message(Message.Category.info, "連線測試失敗，請檢查自定義資料庫設定．"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 建立資料表
		CreateTable createTable = new CreateTable();

		Object sendObj = null;
		return sendObj;

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configRequestClass() {
		this.requestObj = (Class<InstallationRequest>) idv.GoodsManager.installation.api.request.InstallationRequest.class;

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.operater(req, resp);
	}

}
