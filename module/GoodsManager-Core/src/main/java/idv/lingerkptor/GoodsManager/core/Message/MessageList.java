package idv.lingerkptor.GoodsManager.core.Message;

import java.util.List;

public class MessageList {
	private Message.Category category;
	private List<Message> list;

	MessageList() {

	}

	MessageList(Message.Category category) {
		this.category = category;
	}

	public Message.Category getCategory() {
		return category;
	}

	public List<Message> getList() {
		return list;
	}


	public void setList(List<Message> list) {
		this.list = list;

	}

}
