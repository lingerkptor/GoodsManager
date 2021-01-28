package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class ModifyGoodsResponse implements Response {

	public enum Code {
		SUCCESS// 修改成功
		, SQLException// SQL例外
		, SQLFileError// SQL檔案錯誤
		, Other// 其他
	}

	private String code;

	public void setCode(String code) {
		this.code = code;
	};

}
