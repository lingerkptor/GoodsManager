package idv.lingerkptor.GoodsManager.Operator.api.response;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public enum IncreateTagResponse implements Response {
	SQLException, // SQLException 例外發生
	SQLFILEERROR, // SQLFile出錯
	OTHEREXCEPTION, // 其他例外
	TAGISEXIST, // TAG已經存在
	TAGNAMEISEMPTY, // 標籤名稱為空
	INCREATEFAILURE,//標籤建立失敗 
	SUCESS;// 建立成功

	@SuppressWarnings("unused")
	private String code = this.name();

	@Override
	public void setAttribute(HttpSession session) {

	}

}
