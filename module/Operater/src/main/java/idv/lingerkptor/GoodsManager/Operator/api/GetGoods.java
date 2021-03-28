package idv.lingerkptor.GoodsManager.Operator.api;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.GetGoodsDAO;
import idv.lingerkptor.GoodsManager.Operator.DataAccess.GetTagListDAO;
import idv.lingerkptor.GoodsManager.Operator.DataAccess.getPictureListDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.GetGoodsRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetGoodsResponse;
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
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/api/GetGoods")
public class GetGoods extends Service {

	private static final long serialVersionUID = -8414908025259330986L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Text_Plain, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		GetGoodsRequest request = (GetGoodsRequest) requestObj;
		GetGoodsResponse response = new GetGoodsResponse();
		try {
			DataAccessTemplate template = DataAccessCore.getSQLTemplate();
			PreparedStatementCreator getGoods = new GetGoodsDAO(request);
			template.query(getGoods, (ResultSet rs) -> {
				response.setGoods(new Goods(rs.getInt(1), rs.getString(2), rs.getInt(3),
						rs.getInt(4), rs.getString(5), rs.getDate(6).getTime(), rs.getDouble(7),
						rs.getDouble(8), rs.getDouble(9)));
			});
			if (response.getGoods() != null) {
				PreparedStatementCreator getTagList = new GetTagListDAO(request.getGID());
				template.query(getTagList, (ResultSet rs) -> {
					response.addTag(rs.getString(1));
				});
				PreparedStatementCreator getPictureList = new getPictureListDAO(request.getGID());
				template.query(getPictureList, (ResultSet rs) -> {
					response.addPictureKey(rs.getString(1));
				});
			}
		} catch (SQLException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			response.setCode(GetGoodsResponse.Code.SQLException.name());
			return response;
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, e.getMessage()));
			response.setCode(e.getCode().name());
			return response;
		}
		response.setCode(GetGoodsResponse.Code.SUCCESS.name());
		return response;
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = GetGoodsRequest.class;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
