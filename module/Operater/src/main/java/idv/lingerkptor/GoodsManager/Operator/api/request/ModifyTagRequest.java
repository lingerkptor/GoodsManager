package idv.lingerkptor.GoodsManager.Operator.api.request;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class ModifyTagRequest implements Request {
	private String tagName = "";
	private String tagDescription = "";
	private String newTagName = "";

	/**
	 * 取得標籤名稱
	 * 
	 * @return 標籤名稱
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * 取得標籤描述
	 * 
	 * @return 標籤描述
	 */
	public String getTagDescription() {
		return tagDescription;
	}

	/**
	 * 取得新的標籤名稱
	 * 
	 * @return 新的標籤名稱
	 */
	public String getNewTagName() {
		return newTagName;
	}


}
