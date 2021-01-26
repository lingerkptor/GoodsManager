package idv.lingerkptor.GoodsManager.Operator.api.request;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class GetGoodsListRequest implements Request {
	private String[] tags = null;
	private String className = null;
	private String keyword = null;
	private String date = null;
//	private String token = null;
	private String order = null; // id 、class、date
	private int sortIn = 0;// 0=ASC 1=DESC
	private int count = 10;
	private int page = 1;

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

//	/**
//	 * 
//	 * @return
//	 */
//	public String getToken() {
//		return token;
//	}

	/**
	 * 
	 * @return
	 */
	public String getDate() {
		return this.date;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 0=升冪，1=降冪
	 * 
	 * @return the sortIn
	 */
	public int getSortIn() {
		return sortIn;
	}

	/**
	 * 
	 * @return 每頁多少筆資料
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 
	 * @return 頁數
	 */
	public int getPage() {
		return page;
	}

}
