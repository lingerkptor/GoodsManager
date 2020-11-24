package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.Observer;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.Message.MessageManager;
import idv.lingerkptor.GoodsManager.core.api.request.GetMessageRequest;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.response.GetMessageResponse;
import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.RequestType;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType.ResponseType;

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
	@ContentType(reqType = RequestType.Json, respType = ResponseType.Json) // 輸入跟輸出都是Json
	public Response process(Request reqContext) {
		MessageManager msgManager = MessageInit.getMsgManager();
		GetMessageRequest reqObj = (GetMessageRequest) reqContext;
		MessageObserver observer = new MessageObserver();
		observer.reqObj = reqObj;
		observer.update();
		if (observer.msgList.isEmpty()) {
			msgManager.register(observer);
			for (int i = 0; i < 300 && observer.msgList.isEmpty(); i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("MessageObserver 中斷");
				}
			}
			msgManager.logout(observer);
		}
		return GetMessageResponse.getMessageList(observer.msgList);
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
	 * @param resp httpResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.operater(req, resp);
	}

	class MessageObserver implements Observer {
		List<Message> msgList = null;
		GetMessageRequest reqObj;

		@Override
		public void update() {
			msgList = MessageInit.getMsgManager().getMessages(reqObj.getkey(),
					reqObj.getCategory());
		}

	}
}
