package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.IncreateClassificationDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.IncreateClassificationRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.IncreateClassificationResponse;
import idv.lingerkptor.GoodsManager.Operator.api.response.IncreateClassificationResponse.STATECODE;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.RequestType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.ResponseType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/IncreateClassification")
public class IncreateClassification extends Service {
	private static final long serialVersionUID = 358488607999193204L;

	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponseType.Json)
	public Response process(Request requestObj) {
		IncreateClassificationRequest request = (IncreateClassificationRequest) requestObj;
		/**
		 * 取得存放SQL的Properties
		 */
		Properties prop = new Properties();
		try {
			prop.load(new FileReader( //
					new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/IncreateClassification.properties")));
		} catch (NullPointerException | IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "IncreateClassification.properties 出錯"));
			return IncreateClassificationResponse.CreateClassificationFailure(STATECODE.SQLFile);
		}

		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		/*
		 * 資料庫操作
		 */
		try {
			template.update(new IncreateClassificationDAO(prop, request));
		} catch (DAORuntimeException e) {
			return IncreateClassificationResponse.CreateClassificationFailure(e.getCode());
		} catch (SQLException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "新增分類失敗，原因：" + e.getMessage()));
			e.printStackTrace();
			return IncreateClassificationResponse.CreateClassificationFailure(STATECODE.SQLException);
		}
		MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.info, "新增分類成功"));
		return IncreateClassificationResponse.CreateClassificationSucess();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = IncreateClassificationRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
