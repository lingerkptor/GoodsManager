package idv.lingerkptor.GoodsManager.core.Message;

/**
 * 訊息
 * 
 * @author lingerkptor
 *
 */
public class Message {
	/**
	 * 分類的種類 <br/>
	 * err 錯誤 <br/>
	 * warn 警告 <br/>
	 * info 資訊 <br/>
	 */
	public enum Category {
		err, // 錯誤
		warn, // 警告
		info; // 資訊
	}

	/**
	 * 訊息分類
	 */
	private Category category;
	/**
	 * 訊息碼
	 */
	private String msgKey;
	/**
	 * 訊息內容
	 */
	private String context;

	/**
	 * 不給你用
	 */
	@SuppressWarnings("unused")
	private Message() {

	}

	public Message(String key, Category category, String context) {
		this.msgKey = key;
		this.category = category;
		this.context = context;
	}

	/**
	 * 取得訊息內容
	 * 
	 * @return 訊息內容
	 */
	public String getContext() {
		return this.context;
	}

	/**
	 * 取得訊息分類
	 * 
	 * @return 訊息分類
	 */
	public Category getCategory() {
		return this.category;
	}

	public String getMsgKey() {
		return msgKey;
	}
}
