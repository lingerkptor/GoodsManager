package idv.lingerkptor.GoodsManager.Operator.api.request;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class CreateClassificationRequest implements Request {
	private String classificationName = "";
	private String parentClassificationName = "";

	@Override
	public void setAttribute(HttpSession session) {

	}

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
