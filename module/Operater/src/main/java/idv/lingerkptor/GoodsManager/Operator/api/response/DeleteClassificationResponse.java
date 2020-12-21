package idv.lingerkptor.GoodsManager.Operator.api.response;


import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class DeleteClassificationResponse implements Response {

	public enum STATECODE {
		SQLFILEERROR, // SQL檔案出錯
		CLASSIFICATIONNONEXIST, // 分類不存在
		DELETEFAILURE, // 刪除失敗
		DELETESUCESS, // 刪除成功
		SQLEXCEPTION // SQL例外
	};

	@SuppressWarnings("unused")
	private String Code ;

	private DeleteClassificationResponse() {

	}



	public static Response failure(Enum<?> code) {
		DeleteClassificationResponse response = new DeleteClassificationResponse();
		response.Code = code.name();
		return response;
	}

	public static Response sucess() {
		DeleteClassificationResponse response = new DeleteClassificationResponse();
		response.Code = STATECODE.DELETESUCESS.name();
		return response;
	}

}
