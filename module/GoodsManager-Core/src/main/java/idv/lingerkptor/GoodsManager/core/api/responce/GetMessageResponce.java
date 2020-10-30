package idv.lingerkptor.GoodsManager.core.api.responce;

import java.util.List;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Message.Message;

public class GetMessageResponce implements Responce {
	private List<Message> messageList = null;

	public static GetMessageResponce getMessageList(List<Message> messageList) {
		GetMessageResponce responce = new GetMessageResponce();
		responce.messageList = messageList;
		return responce;
	}

	@Override
	public void setAttribute(HttpSession session) {
		try {
		Message msg = messageList.get(messageList.size()-1);
		session.setAttribute("lastMessageKey", msg.getMsgKey());
		}catch(NullPointerException e) {
			return ;
			// 沒有新的內容就會發生這個例外
		}
	}


}
