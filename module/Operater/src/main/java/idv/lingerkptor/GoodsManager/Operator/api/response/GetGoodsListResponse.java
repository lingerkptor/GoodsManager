package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.GoodsManager.core.bean.Goods;

import java.util.LinkedList;
import java.util.List;

public class GetGoodsListResponse implements Response {
	public enum Code {
		SQLFileError // SQLFile錯誤
		, SQLException// SQLException
		, SUCCESS // 成功
		,;

		public Response getResponse() {
			GetGoodsListResponse response = new GetGoodsListResponse();
			response.Code = this.name();
			return response;
		}
	}

	private GetGoodsListResponse() {
	}

	@SuppressWarnings("unused")
	private String Code = "";
	@SuppressWarnings("unused")
	private int page = 1;
//	private String token = "";
	@SuppressWarnings("unused")
	private int pages = 0;
	private List<Goods> goodsList = new LinkedList<Goods>();

	public void appendGoods(Goods goods) {
		goodsList.add(goods);
	}

//	public void setToken(String token) {
//		this.token = token;
//	}

	public void setPages(int pageCount) {
		this.pages = pageCount;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

}
