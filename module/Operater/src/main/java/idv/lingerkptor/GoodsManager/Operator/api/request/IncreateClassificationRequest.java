package idv.lingerkptor.GoodsManager.Operator.api.request;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class IncreateClassificationRequest implements Request {
	private String classificationName = "";
	private String parentClassificationName = "";

	/**
	 * 取得要建立的分類名稱
	 * 
	 * @return the className 分類名稱
	 */
	public String getClassificationName() {
		return classificationName;
	}

	/**
	 * 取得父分類名稱
	 * 
	 * @return the parentClassName 父分類名稱
	 */
	public String getParentClassificationName() {
		return parentClassificationName;
	}

}
