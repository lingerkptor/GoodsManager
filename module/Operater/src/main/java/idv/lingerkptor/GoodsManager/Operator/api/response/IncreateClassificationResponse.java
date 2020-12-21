package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class IncreateClassificationResponse implements Response {
	public enum STATECODE {
		SQLFile, // SQL檔案錯誤，可能是找不到檔案，或是讀取有問題
		SQLException, // 執行SQL產生的錯誤．
		PARENTCLASSNONEXIST, // 找不到上層分類
		CLASSISEXIST, // 分類已存在
		CREATECLASSSUCESS, // 分類建立成功
		LOSTCLASS, // 已建立商品，但是查詢不到．
		DATAERROR;// 資料內容錯誤
	};

	@SuppressWarnings("unused")
	private String Code = "";

	public static Response CreateClassificationFailure(Enum<?> CODE) {
		IncreateClassificationResponse response = new IncreateClassificationResponse();
		response.Code = CODE.name();
		return response;
	}

	public static Response CreateClassificationSucess() {
		IncreateClassificationResponse response = new IncreateClassificationResponse();
		response.Code = STATECODE.CREATECLASSSUCESS.name();
		return response;
	}

}
