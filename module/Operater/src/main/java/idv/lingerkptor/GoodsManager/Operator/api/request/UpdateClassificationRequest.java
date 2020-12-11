package idv.lingerkptor.GoodsManager.Operator.api.request;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class UpdateClassificationRequest implements Request {

	private String classificationName = null;
	private String classificationNewName = null;
	private String parentClassificationName = null;

	@Override
	public void setAttribute(HttpSession session) {

	}

	public int getUpdateContent() {
		int result = 0;
		if (classificationName != null)
			result += 1;
		if (parentClassificationName != null)
			result += 2;
		return result;
	}

	/**
	 * 舊名稱
	 * 
	 * @return the classificationName
	 */
	public String getClassificationName() {
		return classificationName;
	}

	/**
	 * 上層分類名稱
	 * 
	 * @return the parentClassificationName
	 */
	public String getParentClassificationName() {
		return parentClassificationName;
	}

	/**
	 * 新名稱
	 * 
	 * @return the classificationNewName
	 */
	public String getClassificationNewName() {
		return this.classificationNewName;
	}

}
