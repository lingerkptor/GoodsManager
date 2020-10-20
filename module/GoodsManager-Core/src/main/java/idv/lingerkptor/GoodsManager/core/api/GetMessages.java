package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ClassName;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.RequestType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.ResponceType;

/**
 * 取得訊息
 * 
 * @author lingerkptor
 *
 */
@WebServlet("/api/getMessages")
public class GetMessages extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = 233903312735923849L;

	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponceType.Json) // 輸入跟輸出都是Json
	@ClassName(reqObjClass = "idv.lingerkptor.GoodsManager.core.api.GetMessages$RequestContext") // 請求物件類別
	public Object process(Object reqObj) {
		RequestContext reqContext = (RequestContext) reqObj;

		@SuppressWarnings("unchecked")
		List<Message> unSendMsgList = (List<Message>) this.session.getAttribute(reqContext.getCategory());
		return unSendMsgList;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doService(req, resp);
	}

	// json封包
	// {
	// category:err info warn
	// }
	/**
	 * 請求物件類別
	 * 
	 * @author lingerkptor
	 *
	 */
	protected class RequestContext {
		private Message.Category category;

		RequestContext() {
		}

		public String getCategory() {
			return category.toString();
		}
	}

}
