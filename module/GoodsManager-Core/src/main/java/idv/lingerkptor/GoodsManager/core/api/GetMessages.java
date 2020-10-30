package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.Message.MessageManager;
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
	 * @return 回傳要傳送過去的訊息清單
	 */
	@Override
	@ContentType(reqType = RequestType.Json, respType = ResponceType.Json) // 輸入跟輸出都是Json
	public Responce process(Request reqContext) {

		MessageManager msgManager = MessageInit.getMsgManager();
		GetMessageRequest reqObj = (GetMessageRequest) reqContext;
		if(!msgManager.searchRecipient(reqObj.getToken()))
			return GetMessageResponce.getMessageList(null);
		List<Message> messageList = msgManager.getMessages(reqObj.getkey(), reqObj.getCategory());
		System.out.println("getCategory: "+reqObj.getCategory());

		return GetMessageResponce.getMessageList(messageList);
	}

	/**
	 * 設定請求主體的類別
	 */
	@Override
	protected void configRequestClass() {
		this.requestClass = GetMessageRequest.class;
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
