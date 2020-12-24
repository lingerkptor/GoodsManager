package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class ModifyTagResponse implements Response {

	public enum CODE {
		SUCCESS// 修改成功
		, SQLFILEERROR// 存放SQL的檔案錯誤
		, SQLEXCEPTION// SQL例外
		, TAGNAMEISNOTEXIST// 標籤名稱不存在
		, MODIFYTAGNAMEFAILURE// 修改標籤名稱失敗
		, MODIFYTAGDESCRIPTIONFAILURE,;// 修改標籤描述失敗

		public Response getResponse() {
			ModifyTagResponse response = new ModifyTagResponse();
			response.code = this.name();
			return response;
		}
	}

	@SuppressWarnings("unused")
	private String code;

	private ModifyTagResponse() {
	};
}
