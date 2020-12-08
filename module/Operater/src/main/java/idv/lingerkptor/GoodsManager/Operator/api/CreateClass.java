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

import idv.lingerkptor.GoodsManager.Operator.DataAccess.CreateClassDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.CreateClassRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.AddGoodsResponse;
import idv.lingerkptor.GoodsManager.Operator.api.response.CreateClassResponse;
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

@WebServlet("/api/CreateClass")
public class CreateClass extends Service {
	private static final long serialVersionUID = 358488607999193204L;

	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponseType.Json) 
	public Response process(Request requestObj) {
		CreateClassRequest request = (CreateClassRequest) requestObj;
		/**
		 * 取得存放SQL的Properties
		 */
		Properties prop = new Properties();
		try {
			prop.load(new FileReader( //
					new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/CreateClass.properties")));
		} catch (NullPointerException | IOException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "AddGoods.properties 出錯"));
			return AddGoodsResponse.addGoodsFailure(CreateClassResponse.STATECODE.SQLFile);
		}

		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		/*
		 * 資料庫操作
		 */
		try {
			template.update(new CreateClassDAO(prop, request));
		} catch (DAORuntimeException e) {
			return CreateClassResponse.CreateClassFailure(e.getCode());
		} catch (SQLException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "新增分類失敗，原因：" + e.getMessage()));
			e.printStackTrace();
			return CreateClassResponse
					.CreateClassFailure(CreateClassResponse.STATECODE.SQLException);
		}
		MessageInit.getMsgManager().deliverMessage(
				new Message(Message.Category.info, "新增分類成功"));
		return CreateClassResponse.CreateClassSucess();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = CreateClassRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}
	

}
