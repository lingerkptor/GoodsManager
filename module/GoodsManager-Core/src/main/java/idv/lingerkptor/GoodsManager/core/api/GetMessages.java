package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import idv.lingerkptor.GoodsManager.core.api.request.*;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.RequestType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.ResponceType;

/**
 * 取得訊息
 *
 * @author lingerkptor
 */

@SuppressWarnings("hiding")
@WebServlet("/api/getMessages")
public class GetMessages<GetMessageRequest, List> extends Service<GetMessageRequest, List> {
	/**
	 *
	 */
	private static final long serialVersionUID = 233903312735923849L;

	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponceType.Json) // 輸入跟輸出都是Json
	public List process(GetMessageRequest reqContext) {
		/**
		 * Java的泛型是earse type，所以在編譯後的程式碼泛型類別，都會是Object ，所以還是要轉型．
		 */
		idv.lingerkptor.GoodsManager.core.api.request.GetMessageRequest reqObj = (idv.lingerkptor.GoodsManager.core.api.request.GetMessageRequest) reqContext;
		@SuppressWarnings("unchecked")
		List unSendMsgList = (List) this.session.getAttribute(reqObj.getCategory());
		return unSendMsgList;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void configRequestClass() {
		this.requestObj =  (Class<GetMessageRequest>) idv.lingerkptor.GoodsManager.core.api.request.GetMessageRequest.class;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.operater(req, resp);
	}

}
