package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;
import idv.lingerkptor.GoodsManager.core.bean.Goods;

public class GetGoodsResponse implements Response {
	public enum Code {
		SQLException// SQL例外
		, SQLFileError// SQL檔案錯誤
		, SUCCESS// 成功
	};

	@SuppressWarnings("unused")
	private String code = "";
	private Goods goods = null;

	public void setCode(String code) {
		this.code = code;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public void addTag(String tagName) {
		this.goods.appendTag(tagName);
	}

	public void addPictureKey(String picKey) {
		this.goods.appendPicKey(picKey);
	}

	public Goods getGoods() {
		return this.goods;
	}

}
