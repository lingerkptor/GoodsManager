package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.GetTagListDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.GetTagListRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetTagListResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/getTagList")
public class GetTagList extends Service {

	private static final long serialVersionUID = 8085537269996881927L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Text_Plain, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {

		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		GetTagListDAO dao = new GetTagListDAO();
		try {
			template.query(dao, dao);
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, e.getMessage()));
			return ((GetTagListResponse.STATECODE) e.getCode()).getResponse();
		} catch (SQLException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, e.getMessage()));
			return GetTagListResponse.STATECODE.SQLFILEERROR.getResponse();
		}
		return dao.getResponse();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = GetTagListRequest.class;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
