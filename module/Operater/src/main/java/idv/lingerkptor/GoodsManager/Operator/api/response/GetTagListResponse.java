package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.DataObject.Tag;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

import java.util.LinkedList;
import java.util.List;

public class GetTagListResponse implements Response {
	public enum STATECODE {
		SQLFILEERROR, // SQL檔案錯誤，可能是找不到檔案，或是讀取有問題
		SQLEXCEPTION, // 執行SQL產生的錯誤．
		SUCCESS // 查詢成功
		;

		public GetTagListResponse getResponse() {
			GetTagListResponse response = new GetTagListResponse();
			response.code = this.name();
			return response;
		}
	};

	private GetTagListResponse() {
	};

	@SuppressWarnings("unused")
	private String code;
	private List<Tag> tagList = new LinkedList<Tag>();

	public void addTag(Tag tag) {
		tagList.add(tag);
	}
}
