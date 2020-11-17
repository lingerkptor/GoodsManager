package idv.lingerkptor.GoodsManager.core.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import idv.lingerkptor.GoodsManager.core.Observer;
import idv.lingerkptor.GoodsManager.core.Message.Message.Category;

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
	/**
	 * 訊息清單
	 */
	private LinkedList<Message> messageList = new LinkedList<Message>();
	/**
	 * 觀察者清單(尚未完成實作)
	 */
	private List<Observer> recipientList = new LinkedList<Observer>();

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
	public List<Message> getMessages(String key, Message.Category[] cate) {
		LinkedList<Message> list = new LinkedList<Message>();
		// 避免多人存取list，先將鎖住．
		synchronized (messageList) {
			// 如果沒有訊息，就不傳訊息
			if (messageList.isEmpty())
				return null;

			List<Category> cateList = Arrays.asList(cate);
			for (int i = messageList.indexOf(messageMap.get(key)) + 1; i < messageList
					.size(); i++) {
				Message msg = messageList.get(i);
				if (cateList.contains(msg.getCategory())) {
					list.add(msg);
				}
			}

		}
		return list;
	}

	/**
	 * 交付訊息，給予管理．
	 */
	public void deliverMessage(Message message) {
		URI uri = null;
		switch (message.getCategory()) {
		case err:
			uri = URIMap.get("err");
			break;
		case warn:
			uri = URIMap.get("warn");
			break;
		case info:
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
			 * 如果沒有接收者就清空資料
			 */
			if (recipientList.isEmpty()) {
				messageMap.clear();
				messageList.clear();
				return;
			}
			synchronized (messageMap) {
				messageMap.put(message.getMsgKey(), message);
			}
			synchronized (messageList) {
				this.messageList.add(message);
			}
			/**
			 * 通知觀察者可以來更新了
			 */
			recipientList.stream().forEach(observer -> {
				observer.update();
			});
		}
	}

	/**
	 * 註冊接收者
	 */
	public void register(Observer observer) {
		synchronized (recipientList) {
			this.recipientList.add(observer);
		}
	}

	/**
	 * 註銷接收者
	 */
	public void logout(Observer observer) {
		synchronized (recipientList) {
			this.recipientList.remove(observer);
		}
	}

}
