package idv.lingerkptor.GoodsManager.Operator.api.request;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class GetGoodsListRequest implements Request {
	private String[] tags = null;
	private String className = null;
	private String keyword = null;
	private String date = null;
	private String token = null;

	/**
	 * @return the tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return the keyword of GoodsName
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 
	 * @return
	 */
	public String getDate() {
		return this.date;
	}

}
