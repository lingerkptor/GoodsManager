package idv.lingerkptor.GoodsManager.Operator.api.response;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class UpdateClassificationResponse implements Response {

	public enum STATECODE {
		SQLFILEERROR, // SQLFile出錯
		SQLEXCEPTION, // SQLException 例外發生
		NONUPDATECLASSIFICATION, // 沒有要更新
		OTHEREXCEPTION, // 其他例外
		UPDATENAMEFAILURE, // 更新名稱失敗
		UPDATEPARENTFAILURE, // 更新上層分類失敗
		UPDATESUCESS,// 更新成功
		NEWNAMEISEXIST;//新名稱已存在
	}

	@SuppressWarnings("unused")
	private String Code = "";

	private UpdateClassificationResponse() {

	}

	@Override
	public void setAttribute(HttpSession session) {

	}

	public static Response failure(Enum<?> code) {
		UpdateClassificationResponse response = new UpdateClassificationResponse();
		response.Code = code.name();
		return response;
	}

	public static Response sucess() {
		UpdateClassificationResponse response = new UpdateClassificationResponse();
		response.Code = STATECODE.UPDATESUCESS.name();
		return response;
	}
}
