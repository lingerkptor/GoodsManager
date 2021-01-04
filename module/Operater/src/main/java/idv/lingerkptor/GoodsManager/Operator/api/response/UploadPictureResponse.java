package idv.lingerkptor.GoodsManager.Operator.api.response;

import java.util.List;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class UploadPictureResponse implements Response {

	public enum Code {
		SECCESS // 上傳成功
		, STOREFAILURE// 檔案儲存失敗
		, SQLFILEERROR// 存放SQL檔案錯誤
		, SQLEXCEPTION// SQL例外
		,;

		public UploadPictureResponse getResponse(List<String> hashcodeList) {
			UploadPictureResponse response = new UploadPictureResponse();
			response.code = this.name();
			response.hash = hashcodeList;
			return response;
		}
	};

	@SuppressWarnings("unused")
	private String code = "";
	@SuppressWarnings("unused")
	private List<String> hash;

}
