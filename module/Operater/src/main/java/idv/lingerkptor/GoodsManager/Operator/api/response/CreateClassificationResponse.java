package idv.lingerkptor.GoodsManager.Operator.api.response;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class CreateClassificationResponse implements Response {
	public enum STATECODE {
		SQLFile, // SQL檔案錯誤，可能是找不到檔案，或是讀取有問題
		SQLException, // 執行SQL產生的錯誤．
		PARENTCLASSNONEXIST, // 找不到上層分類
		CLASSISEXIST, // 分類已存在
		CREATECLASSSUCESS, // 分類建立成功
		LOSTCLASS;// 已建立商品，但是查詢不到．
	};

	@SuppressWarnings("unused")
	private String Code = "";

	@Override
	public void setAttribute(HttpSession session) {

	}

	public static Response CreateClassificationFailure(Enum<?> CODE) {
		CreateClassificationResponse response = new CreateClassificationResponse();
		response.Code = CODE.name();
		return response;
	}

	public static Response CreateClassificationSucess() {
		CreateClassificationResponse response = new CreateClassificationResponse();
		response.Code = STATECODE.CREATECLASSSUCESS.name();
		return response;
	}

}
