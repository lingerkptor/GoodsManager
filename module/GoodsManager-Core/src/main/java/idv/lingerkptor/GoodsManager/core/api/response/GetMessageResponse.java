package idv.lingerkptor.GoodsManager.core.api.response;

import java.util.List;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Message.Message;

public class GetMessageResponse implements Response ,SetableAttributeBySession{
	private List<Message> messageList = null;

	public static GetMessageResponse getMessageList(List<Message> messageList) {
		GetMessageResponse Response = new GetMessageResponse();
		Response.messageList = messageList;
		return Response;
	}

	@Override
	public void setAttribute(HttpSession session) {
		if (messageList != null && !messageList.isEmpty()) {
			Message msg = messageList.get(messageList.size() - 1);
			session.setAttribute("lastMessageKey", msg.getMsgKey());
		}
	}

}
