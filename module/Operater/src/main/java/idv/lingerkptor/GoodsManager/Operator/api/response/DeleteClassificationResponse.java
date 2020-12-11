package idv.lingerkptor.GoodsManager.Operator.api.response;

import javax.servlet.http.HttpSession;

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
	private String STATE ;

	private DeleteClassificationResponse() {

	}

	@Override
	public void setAttribute(HttpSession session) {

	}

	public static Response failure(Enum<?> code) {
		DeleteClassificationResponse response = new DeleteClassificationResponse();
		response.STATE = code.name();
		return response;
	}

	public static Response sucess() {
		DeleteClassificationResponse response = new DeleteClassificationResponse();
		response.STATE = STATECODE.DELETESUCESS.name();
		return response;
	}

}
