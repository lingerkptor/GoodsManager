package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class DeleteGoodsResponse implements Response {

	public enum Code {
		SQLFileError// SQL檔案錯誤
		, SQLException // SQL例外
		, SUCCESS// 成功

	}

	@SuppressWarnings("unused")
	private String code;

	public void setCode(String code) {
		this.code = code;
	};

}
