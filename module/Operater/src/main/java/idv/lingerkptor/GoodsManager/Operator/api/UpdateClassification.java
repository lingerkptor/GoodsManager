package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.UpdateClassificationDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.UpdateClassificationRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.UpdateClassificationResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/UpdateClassification")
public class UpdateClassification extends Service {
	private static final long serialVersionUID = -3280257126197653333L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		UpdateClassificationRequest request = (UpdateClassificationRequest) requestObj;
		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		try {
			template.update(new UpdateClassificationDAO(request));
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, e.getMessage()));
			return UpdateClassificationResponse.failure(e.getCode());
		} catch (SQLException e) {
			e.printStackTrace();
			return UpdateClassificationResponse
					.failure(UpdateClassificationResponse.STATECODE.SQLEXCEPTION);
		}
		return UpdateClassificationResponse.sucess();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = UpdateClassificationRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
