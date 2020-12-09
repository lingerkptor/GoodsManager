package idv.lingerkptor.GoodsManager.Operator.api;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.GetClassificationListDAO;
import idv.lingerkptor.GoodsManager.Operator.api.request.GetClassificationListRequest;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetClassificationListResponse;
import idv.lingerkptor.GoodsManager.Operator.api.response.GetClassificationListResponse.STATECODE;
import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.Service;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;

@WebServlet("/api/GetClassificationList")
public class GetClassificationList extends Service {
	private static final long serialVersionUID = -4749446797184829317L;

	@Override
	@ContentType(reqType = ContentType.RequestType.Text_Plain, respType = ContentType.ResponseType.Json)
	public Response process(Request requestObj) {
		Properties prop = new Properties();
		try {
			prop.load(
					new FileReader(new File(ConfigReader.getConfigReader().getDBConfig().getSqlURL()
							+ "/GetClassificationList.properties")));
		} catch (NullPointerException | IOException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "GetClassificationList.properties 出錯"));
			return GetClassificationListResponse.getClassificationListFailure(STATECODE.SQLFile);
		}
		DataAccessTemplate template = DataAccessCore.getSQLTemplate();
		GetClassificationListDAO dao = new GetClassificationListDAO(prop);
		try {
			template.query(dao, dao);
		} catch (SQLException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "GetClassificationList SQl 出錯"));
			return GetClassificationListResponse
					.getClassificationListFailure(STATECODE.SQLException);
		}

		return GetClassificationListResponse.getClassificationListSucess(dao.getClassList());
	}

	@Override
	protected void configRequestClass() {
		this.requestClass = GetClassificationListRequest.class;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

}
