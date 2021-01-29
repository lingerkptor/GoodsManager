package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.DeleteGoodsDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.DeleteGoodsRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.DeleteGoodsResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/DeleteGoods")
public class DeleteGoods extends Service {

	private static final long serialVersionUID = 4257746692299354367L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		DeleteGoodsRequest request = (DeleteGoodsRequest) requestObj;
		DeleteGoodsResponse response = new DeleteGoodsResponse();
		DeleteGoodsDAO dao = new DeleteGoodsDAO(request);
		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		try {
			template.update(dao);
		} catch (SQLException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			response.setCode(DeleteGoodsResponse.Code.SQLException.name());
			return response;
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			response.setCode(DeleteGoodsResponse.Code.SQLException.name());
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			response.setCode(DeleteGoodsResponse.Code.SQLException.name());
			return response;
		}
		response.setCode(DeleteGoodsResponse.Code.SUCCESS.name());
		return response;
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = DeleteGoodsRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
