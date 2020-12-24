package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.ModifyTagDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.ModifyTagRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.ModifyTagResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

@WebServlet("/api/modifyTag")
public class ModifyTag extends Service {
	private static final long serialVersionUID = 808260757289434045L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		ModifyTagRequest request = (ModifyTagRequest) requestObj;
		if (request.getTagName().equals("")) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "標籤名稱:" + request.getTagName() + "不存在．"));
			return ModifyTagResponse.CODE.TAGNAMEISNOTEXIST.getResponse();
		}
		try {
			DataAccessCore.getSQLTemplate().update(new ModifyTagDAO(request));
		} catch (SQLException e) {
			return ModifyTagResponse.CODE.SQLEXCEPTION.getResponse();
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, e.getMessage()));
			return ((ModifyTagResponse.CODE) e.getCode()).getResponse();
		}

		return ModifyTagResponse.CODE.SUCCESS.getResponse();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = ModifyTagRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
