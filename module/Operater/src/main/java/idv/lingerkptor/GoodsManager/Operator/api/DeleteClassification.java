package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.DeleteClassificationDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.DeleteClassificationRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.DeleteClassificationResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/DeleteClassification")
public class DeleteClassification extends Service {
	private static final long serialVersionUID = -4326149035067312616L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		DeleteClassificationRequest request = (DeleteClassificationRequest) requestObj;
		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		try {
			template.update(new DeleteClassificationDAO(request));
		} catch (SQLException e) {
			return DeleteClassificationResponse
					.failure(DeleteClassificationResponse.STATECODE.SQLEXCEPTION);
		} catch (DAORuntimeException e) {
			return DeleteClassificationResponse.failure(e.getCode());
		}
		return DeleteClassificationResponse.sucess();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = DeleteClassificationRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
