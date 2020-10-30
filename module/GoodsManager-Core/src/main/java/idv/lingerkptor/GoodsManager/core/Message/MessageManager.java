package idv.lingerkptor.GoodsManager.core.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

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
	private transient Map<String, Message> messageMap = new HashMap<String, Message>();
	/*
	 * 錯誤清單
	 */
	private transient LinkedList<Message> err = new LinkedList<Message>();
	/*
	 * 資訊清單
	 */
	private transient LinkedList<Message> info = new LinkedList<Message>();
	/*
	 * 警告清單
	 */
	private transient LinkedList<Message> warn = new LinkedList<Message>();

	private List<String> recipientList = new LinkedList<String>();

	private transient Map<String, URI> URIMap = new HashMap<String, URI>();

	@SuppressWarnings("unused")
	private MessageManager() {
	}

	public MessageManager(MessageConfig msgconfig) {
		this.setConfig(msgconfig);
	}

	public void setConfig(MessageConfig msgconfig) {
		File rootMsgDir = new File(msgconfig.getLocation());
		try {
			if (!rootMsgDir.exists()) {
				System.out.println("rootMsgDir MKDIR.");
				rootMsgDir.mkdirs();
			}
			URI location = rootMsgDir.toURI();
			File dir;
			if (!(dir = new File(location.getRawPath() + "\\err")).exists())
				dir.mkdir();
			URIMap.put("err", dir.toURI());
			if (!(dir = new File(location.getRawPath() + "\\warn")).exists())
				dir.mkdir();
			URIMap.put("warn", dir.toURI());
			if (!(dir = new File(location.getRawPath() + "\\info")).exists())
				dir.mkdir();
			URIMap.put("info", dir.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取得還沒收到的訊息清單
	 * 
	 * @param key  最後收到訊息的訊息碼
	 * @param cate 想收到的訊息分類
	 * @return 訊息清單
	 * @throws Exception
	 */
	public List<Message> getMessages(String key, Message.Category cate) {
		LinkedList<Message> list = null;
		switch (cate) {
		case err:
			list = this.err;
			break;
		case info:
			list = this.info;
			break;
		case warn:
			list = this.warn;
			break;
		}
		// 避免多人存取list，先將鎖住．
		synchronized (list) {
			// 如果沒有訊息，就不傳訊息
			if (list.isEmpty())
				return null;

			// 如果從沒傳過訊息，將訊息都傳過去
			// 為了避免list被修改，所以用clone的方式重新複製一份清單交付
			Message msg = messageMap.get(key);
			if (msg == null) {
				@SuppressWarnings("unchecked") // 淺clone
				List<Message> newList = (List<Message>) list.clone();
				return newList;
			}
			System.out.println(msg.getContext());
			// 如果沒有新的訊息，就不傳訊息
			int index = list.indexOf(msg) + 1;
			if (index == list.size())
				return null;
			List<Message> newList = new LinkedList<Message>();
			while (index < list.size()) {
				newList.add(list.get(index++));
			}
			return newList;
			/** return list.subList(index, list.size() - 1);
			 * 不能用這個，它不是建立一個list，而是做一個reference.．會有修改原來的集合的風險．
			*/
		}
	}

	/**
	 * 交付訊息，給予管理．
	 */
	public void deliverMessage(Message message) {
		List<Message> list = null;
		URI uri = null;
		switch (message.getCategory()) {
		case err:
			list = this.err;
			uri = URIMap.get("err");
			break;
		case warn:
			list = this.warn;
			uri = URIMap.get("warn");
			break;
		case info:
			list = this.info;
			uri = URIMap.get("info");
			break;
		}
		File file = new File(uri.getRawPath() + message.getMsgKey() + ".json");
		try {
			file.createNewFile();
			Gson gson = new Gson();
			Writer fileWriter = new FileWriter(file);
			gson.toJson(message, fileWriter);
			fileWriter.close();
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}

		synchronized (recipientList) {
			/**
			 * 如果都沒有接收者就直接離開，
			 */
			if (!recipientList.isEmpty()) {
				synchronized (messageMap) {
					messageMap.put(message.getMsgKey(), message);
				}
				synchronized (list) {
					list.add(message);
				}
			}
		}

	}

	/**
	 * 查詢接收者
	 */
	public boolean searchRecipient(String token) {
		synchronized (recipientList) {
			return this.recipientList.contains(token);
		}
	}

	/**
	 * 註冊接收者
	 */
	public void register(String token) {
		synchronized (recipientList) {
			this.recipientList.add(token);
		}
	}

	/**
	 * 註銷接收者
	 */
	public void logout(String token) {
		synchronized (recipientList) {
			this.recipientList.remove(token);
			if (recipientList.isEmpty()) {
				messageMap.clear();
				err.clear();
				info.clear();
				warn.clear();
			}
		}
	}

}
