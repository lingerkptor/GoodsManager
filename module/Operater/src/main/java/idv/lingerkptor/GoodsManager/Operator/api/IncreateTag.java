package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.IncreateTagDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.IncreateTagRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.IncreateTagResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/IncreateTag")
public class IncreateTag extends Service {
	private static final long serialVersionUID = 3256026907497091810L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		IncreateTagRequest request = (IncreateTagRequest) requestObj;
		if (request.getTagName().equals("")) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "標籤名稱為空．"));
			return IncreateTagResponse.TAGNAMEISEMPTY;
		}
		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		try {
			template.update(new IncreateTagDAO(request));
		} catch (SQLException e) {
			return IncreateTagResponse.SQLException;
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, e.getMessage()));
			return (IncreateTagResponse) e.getCode();
		}

		return IncreateTagResponse.SUCESS;
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = IncreateTagRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
