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

import idv.lingerkptor.GoodsManager.Operator.DataAccess.CreateClassificationDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.CreateClassificationRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.CreateClassificationResponse;
import idv.lingerkptor.GoodsManager.Operator.api.response.CreateClassificationResponse.STATECODE;
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

@WebServlet("/api/CreateClassification")
public class CreateClass extends Service {
	private static final long serialVersionUID = 358488607999193204L;

	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponseType.Json)
	public Response process(Request requestObj) {
		CreateClassificationRequest request = (CreateClassificationRequest) requestObj;
		/**
		 * 取得存放SQL的Properties
		 */
		Properties prop = new Properties();
		try {
			prop.load(new FileReader( //
					new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/CreateClassification.properties")));
		} catch (NullPointerException | IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "CreateClassification.properties 出錯"));
			return CreateClassificationResponse.CreateClassificationFailure(STATECODE.SQLFile);
		}

		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		/*
		 * 資料庫操作
		 */
		try {
			template.update(new CreateClassificationDAO(prop, request));
		} catch (DAORuntimeException e) {
			return CreateClassificationResponse.CreateClassificationFailure(e.getCode());
		} catch (SQLException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "新增分類失敗，原因：" + e.getMessage()));
			e.printStackTrace();
			return CreateClassificationResponse.CreateClassificationFailure(STATECODE.SQLException);
		}
		MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.info, "新增分類成功"));
		return CreateClassificationResponse.CreateClassificationSucess();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = CreateClassificationRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
