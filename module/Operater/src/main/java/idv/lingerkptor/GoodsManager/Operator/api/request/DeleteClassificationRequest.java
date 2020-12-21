package idv.lingerkptor.GoodsManager.Operator.api.request;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class DeleteClassificationRequest implements Request {
	private String classificationName;

	/**
	 * 取得要刪除的分類名稱
	 * 
	 * @return the classificationName
	 */
	public String getClassificationName() {
		return classificationName;
	}

}
