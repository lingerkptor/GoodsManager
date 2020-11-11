package idv.lingerkptor.GoodsManager.core.api.request;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Message.Message;

//json封包
// {
// category:err info warn
// }

/**
 * 請求物件類別
 *
 * @author lingerkptor
 */
public class GetMessageRequest implements Request {
	private Message.Category category;
	/**
	 * 最後傳出的訊息碼
	 */
	private String key = null;

	private String token = null;

	GetMessageRequest() {
	}

	public Message.Category getCategory() {
		return category;
	}

	public String getkey() {
		return key;
	}

	@Override
	public void setAttribute(HttpSession session) {
		this.token = (String) session.getAttribute("token");
		this.key = (String) session.getAttribute("lastMessageKey");
	}

	public String getToken() {
		return token;
	}
}