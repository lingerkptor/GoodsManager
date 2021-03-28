package idv.lingerkptor.GoodsManager.Operator.api;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.GetGoodsListData;
import idv.lingerkptor.GoodsManager.Operator.DataAccess.GetGoodsListPage;
import idv.lingerkptor.GoodsManager.Operator.api.request.GetGoodsListRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetGoodsListResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.GoodsManager.core.bean.Goods;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/api/getGoodsList")
public class GetGoodsList extends Service {

	private static final long serialVersionUID = 1413043708942156861L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		GetGoodsListRequest request = (GetGoodsListRequest) requestObj;
		try {
			DataAccessTemplate template = DataAccessCore.getSQLTemplate();
			GetGoodsListResponse response = (GetGoodsListResponse) GetGoodsListResponse.Code.SUCCESS
					.getResponse();

			template.query(new GetGoodsListPage(request), (ResultSet rs) -> {
				if (rs.getInt(2) > 0)
					response.setPages(rs.getInt(1));
			});

			template.query(new GetGoodsListData(request), (ResultSet rs) -> {
				response.appendGoods(new Goods(rs.getInt(1), rs.getString(2), rs.getInt(3),
						rs.getInt(4), rs.getString(5), rs.getDate(6).getTime()));
				response.setPage(request.getPage());
//				if (request.getOrder() != null) {
//					switch (request.getOrder()) {
//					case "class":
//						response.setToken(rs.getString(5));
//						break;
//					case "date":
//						response.setToken(String.valueOf(rs.getDate(6).getTime()));
//						break;
//					default:
//						response.setToken(String.valueOf(rs.getInt(1)));
//					}
//				} else
//					response.setToken(String.valueOf(rs.getInt(1)));
			});

			return response;
		} catch (SQLException e) {
			e.printStackTrace();
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			return GetGoodsListResponse.Code.SQLException.getResponse();
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			return ((GetGoodsListResponse.Code) e.getCode()).getResponse();
		}
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = GetGoodsListRequest.class;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
