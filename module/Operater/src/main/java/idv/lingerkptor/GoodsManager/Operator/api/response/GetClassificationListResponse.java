package idv.lingerkptor.GoodsManager.Operator.api.response;

import java.util.List;

import idv.lingerkptor.GoodsManager.Operator.DataAccess.DataObject.Classification;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class GetClassificationListResponse implements Response {
	public enum STATECODE {
		SQLFile, // SQL檔案錯誤，可能是找不到檔案，或是讀取有問題
		SQLException, // 執行SQL產生的錯誤．
		GetClassificationList // 執行成功
	};

	@SuppressWarnings("unused")
	private String Code = "";
	@SuppressWarnings("unused")
	private List<Classification> classificationList;

	public static GetClassificationListResponse getClassificationListFailure(STATECODE code) {
		GetClassificationListResponse response = new GetClassificationListResponse();
		response.Code = code.name();
		return response;
	}

	public static GetClassificationListResponse getClassificationListSucess(
			List<Classification> list) {
		GetClassificationListResponse response = new GetClassificationListResponse();
		response.Code = STATECODE.GetClassificationList.name();
		response.classificationList = list;
		return response;
	}
}
