package idv.lingerkptor.GoodsManager.core.api.responce;

import java.util.List;

import idv.lingerkptor.GoodsManager.core.Message.Message;

public class GetMessageResponce implements Responce {
	private List<Message> messageList = null;

	public static GetMessageResponce getMessageList(List<Message> messageList) {
		GetMessageResponce responce = new GetMessageResponce();
		responce.messageList = messageList;
		return responce;
	}
}
