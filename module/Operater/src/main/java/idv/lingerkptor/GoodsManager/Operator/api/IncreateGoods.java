package idv.lingerkptor.GoodsManager.Operator.api;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.IncreateGoodsDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.IncreateGoodsRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.IncreateGoodsResponse;
import idv.lingerkptor.GoodsManager.core.DataAccess.DAORuntimeException;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/increateGoods")
public class IncreateGoods extends Service {
	private static final long serialVersionUID = -5305439431604713870L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Json, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		IncreateGoodsRequest request = (IncreateGoodsRequest) requestObj;

		DataAccessTemplate template = DataAccessCore.getSQLTemplate();

		try {
			template.update(new IncreateGoodsDAO(request));
		} catch (SQLException e) {
			e.printStackTrace();
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "商品建立失敗 Message : " + e.getMessage()));
			return IncreateGoodsResponse.Code.SQLException.getResponse();
		} catch (DAORuntimeException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, e.getMessage()));
			return ((IncreateGoodsResponse.Code) e.getCode()).getResponse();
		}
		MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.info, "建立商品成功"));
		return IncreateGoodsResponse.Code.SUCCESS.getResponse();
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = IncreateGoodsRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		this.operater(request, response);
	}
}
