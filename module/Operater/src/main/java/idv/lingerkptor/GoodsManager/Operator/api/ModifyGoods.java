package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.ModifyGoodsDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.ModifyGoodsRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.ModifyGoodsResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/ModifyGoods")
public class ModifyGoods extends Service {

	private static final long serialVersionUID = 692330311522432572L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		ModifyGoodsRequest request = (ModifyGoodsRequest) requestObj;
		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		ModifyGoodsResponse response = new ModifyGoodsResponse();
		try {
			ModifyGoodsDAO modifyGoods = new ModifyGoodsDAO(request);
			template.update(modifyGoods);
		} catch (SQLException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			response.setCode(ModifyGoodsResponse.Code.SQLException.name());
			return response;
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			response.setCode(e.getCode().name());
			return response;
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("message is =>  "+e.getMessage());
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			response.setCode(ModifyGoodsResponse.Code.Other.name());
			return response;
		}

		response.setCode(ModifyGoodsResponse.Code.SUCCESS.name());
		return response;
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = ModifyGoodsRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
