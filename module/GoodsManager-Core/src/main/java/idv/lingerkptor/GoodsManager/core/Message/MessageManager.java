package idv.lingerkptor.GoodsManager.core.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 訊息管理者
 * 
 * @author lingerkptor
 *
 */
public class MessageManager {
	/**
	 * 訊息對照碼
	 */
	private Map<String, Message> messageMap = new HashMap<String, Message>();
	/*
	 * 錯誤清單
	 */
	private LinkedList<Message> err = new LinkedList<Message>();
	/*
	 * 資訊清單
	 */
	private LinkedList<Message> info = new LinkedList<Message>();
	/*
	 * 警告清單
	 */
	private LinkedList<Message> warn = new LinkedList<Message>();

	/**
	 * 取得還沒收到的訊息清單
	 * 
	 * @param key  最後收到訊息的訊息碼
	 * @param cate 想收到的訊息分類
	 * @return 訊息清單
	 * @throws Exception
	 */

	public List<Message> getMessages(String key, Message.Category cate) throws Exception {
		LinkedList<Message> list = null;
		switch (cate) {
		case err:
			list = err;
		case info:
			list = info;
		case warn:
			list = warn;
		default:
			if (list == null)
				throw new Exception("沒有這個訊息種類");
			// 避免多人存取list，先將鎖住．
			synchronized (list) {
				// 如果沒有訊息，就不傳訊息
				if (list.isEmpty())
					return null;

				// 如果從沒傳過訊息，將訊息都傳過去
				// 為了避免list被修改，所以用clone的方式重新複製一份清單交付
				Message msg = messageMap.get(key);
				if (msg == null) {
					@SuppressWarnings("unchecked")
					List<Message> newList = (List<Message>) list.clone();
					return newList;
				}
				// 如果沒有新的訊息，就不傳訊息
				int index = list.indexOf(msg) + 1;
				if (index == list.size())
					return null;

				return list.subList(index, list.size() - 1);
			}
		}
	}

	/**
	 * 交付訊息，給予管理．
	 */
	public void deliverMessage(Message message) {
		List<Message> list = null;
		switch (message.getCategory()) {
		case err:
			list = this.err;
		case warn:
			list = this.warn;
		case info:
			list = this.info;
		default:
			list.add(message);
			break;
		}
	}
}
