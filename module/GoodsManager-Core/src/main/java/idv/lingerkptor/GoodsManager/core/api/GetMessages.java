package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.api.request.GetMessageRequest;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.responce.GetMessageResponce;
import idv.lingerkptor.GoodsManager.core.api.responce.Responce;
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
public class GetMessages extends Service {
	/**
	 *
	 */
	private static final long serialVersionUID = 233903312735923849L;

	/**
	 * 請求的要取得的主體
	 * 
	 * @param reqContext          請求的主體，包含訊息的內容種類
	 * @param <GetMessageRequest> 請求內容的型態
	 * @param <List>              清單型態
	 * @return 回傳要傳送過去的訊息清單
	 */
	@SuppressWarnings("unchecked")
	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponceType.Json) // 輸入跟輸出都是Json
	public Responce process(Request reqContext) {
		GetMessageRequest reqObj = (GetMessageRequest) reqContext;
		/** Deep Clone */
		LinkedList<Message> originMsgList = ((LinkedList<Message>) this.session.getAttribute(reqObj.getCategory()));
		LinkedList<Message> messageList = new LinkedList<Message>();
		for (Message msg : originMsgList) {
			messageList.add(msg.clone());
		}
		originMsgList.clear();

		return GetMessageResponce.getMessageList(messageList);
	}

	/**
	 * 設定請求主體的類別
	 */
	@Override
	protected void configRequestClass() {
		this.requestObj = GetMessageRequest.class;
	}

	/**
	 * HTTP Post方法
	 *
	 * @param req  httpRespuest
	 * @param resp httpResponce
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.operater(req, resp);
	}

}
