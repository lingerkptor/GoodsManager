package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class IncreateGoodsResponse implements Response {
	public enum Code {
		SQLFILEERROR, // SQL檔案錯誤，可能是找不到檔案，或是讀取有問題
		SQLException, // 執行SQL產生的錯誤．
		PICTUREISBINDED, // 圖片已經綁定商品
		CLASSNONEXIST, // 找不到該分類
		LOSTGOODS, // 已建立商品，但是查詢不到．
		SUCCESS;// 建立成功

		public Response getResponse() {
			IncreateGoodsResponse response = new IncreateGoodsResponse();
			response.code = this.name();
			return response;
		}

	};

	@SuppressWarnings("unused")
	private String code = "";

}
