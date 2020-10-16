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

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

/**
 * 訊息管理者
 * 
 * @author lingerkptor
 *
 */
public class MessageManager {
//	/**
//	 * 訊息對照碼
//	 */
//	private Map<String, Message> messageMap = new HashMap<String, Message>();
//	/*
//	 * 錯誤清單
//	 */
//	private LinkedList<Message> err = new LinkedList<Message>();
//	/*
//	 * 資訊清單
//	 */
//	private LinkedList<Message> info = new LinkedList<Message>();
//	/*
//	 * 警告清單
//	 */
//	private LinkedList<Message> warn = new LinkedList<Message>();

	private List<HttpSession> recipientList = new LinkedList<HttpSession>();

	private Map<String, URI> URIMap = new HashMap<String, URI>();
	private URI location;

	@SuppressWarnings("unused")
	private MessageManager() {
	}

	public MessageManager(MessageConfig msgconfig) {
		File rootMsgDir = new File(msgconfig.getLocation());
		try {
			if (!rootMsgDir.exists()) {
				System.out.println("rootMsgDir MKDIR.");
				rootMsgDir.mkdirs();
			}
			location = rootMsgDir.toURI();
//			System.out.println("LocationRawPath" + location.getRawPath());
			File dir;
			if (!(dir = new File(location.getRawPath() + "\\err")).exists())
				dir.mkdir();
//			System.out.println("errRawPath" + dir.toURI().getRawPath());
			URIMap.put("err", dir.toURI());
			if (!(dir = new File(location.getRawPath() + "\\warn")).exists())
				dir.mkdir();
//			System.out.println("warnRawPath" + dir.toURI().getRawPath());
			URIMap.put("warn", dir.toURI());
			if (!(dir = new File(location.getRawPath() + "\\info")).exists())
				dir.mkdir();
//			System.out.println("infoRawPath" + dir.toURI().getRawPath());
			URIMap.put("info", dir.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
//	/**
//	 * 取得還沒收到的訊息清單
//	 * 
//	 * @param key  最後收到訊息的訊息碼
//	 * @param cate 想收到的訊息分類
//	 * @return 訊息清單
//	 * @throws Exception
//	 */
//	public List<Message> getMessages(String key, Message.Category cate) throws Exception {
//		LinkedList<Message> list = null;
//		switch (cate) {
//		case err:
//			list = err;
//		case info:
//			list = info;
//		case warn:
//			list = warn;
//		default:
//			if (list == null)
//				throw new Exception("沒有這個訊息種類");
//			// 避免多人存取list，先將鎖住．
//			synchronized (list) {
//				// 如果沒有訊息，就不傳訊息
//				if (list.isEmpty())
//					return null;
//
//				// 如果從沒傳過訊息，將訊息都傳過去
//				// 為了避免list被修改，所以用clone的方式重新複製一份清單交付
//				Message msg = messageMap.get(key);
//				if (msg == null) {
//					@SuppressWarnings("unchecked")
//					List<Message> newList = (List<Message>) list.clone();
//					return newList;
//				}
//				// 如果沒有新的訊息，就不傳訊息
//				int index = list.indexOf(msg) + 1;
//				if (index == list.size())
//					return null;
//				return list.subList(index, list.size() - 1);
//			}
//		}
//	}

	/**
	 * 交付訊息，給予管理．
	 */

	@SuppressWarnings("unchecked")
	public void deliverMessage(Message message) {
//		List<Message> list = null;
		URI uri = null;
		switch (message.getCategory()) {
		case err:
//			list = this.err;
			uri = URIMap.get("err");
		case warn:
//			list = this.warn;
			uri = URIMap.get("warn");
		case info:
//			list = this.info;
			uri = URIMap.get("info");
		default:
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
			/**
			 * 將訊息派發給user
			 */
			if (!recipientList.isEmpty()) {
				synchronized (recipientList) {
					for (HttpSession user : recipientList) {
						List<Message> msglist = null;
						switch (message.getCategory()) {
						case err:
							msglist = (List<Message>) user.getAttribute(Message.Category.err.toString());
						case warn:
							msglist = (List<Message>) user.getAttribute(Message.Category.warn.toString());
						case info:
							msglist = (List<Message>) user.getAttribute(Message.Category.info.toString());
						default:
							synchronized (msglist) {
								msglist.add(message);
							}
							break;
						}
					}
				}
			}

//			/**
//			 * 如果都沒有接收者就全清除，
//			 */
//			if (recipientList.isEmpty()) {
//				messageMap.clear();
//				err.clear();
//				info.clear();
//				warn.clear();
//				break;
//			}
//			synchronized (messageMap) {
//				messageMap.put(message.getMsgKey(), message);
//			}
//			synchronized (list) {
//				list.add(message);
//			}
			break;
		}
	}

	/**
	 * 註冊接收者
	 */
	public void register(HttpSession user) {
		synchronized (recipientList) {
			this.recipientList.add(user);
			user.setAttribute(Message.Category.err.toString(), new LinkedList<Message>());
			user.setAttribute(Message.Category.warn.toString(), new LinkedList<Message>());
			user.setAttribute(Message.Category.info.toString(), new LinkedList<Message>());
		}
	}

	/**
	 * 註銷接收者
	 */
	public void logout(HttpSession user) {
		synchronized (recipientList) {
			this.recipientList.remove(user);
			user.removeAttribute(Message.Category.err.toString());
			user.removeAttribute(Message.Category.warn.toString());
			user.removeAttribute(Message.Category.info.toString());
		}
	}

}
