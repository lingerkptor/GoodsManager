package idv.lingerkptor.GoodsManager.Operator.api.request;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class CreateClassRequest implements Request{
	private String className="";
	private String parentClassName="";
	

	@Override
	public void setAttribute(HttpSession session) {
		
	}


	/**
	 * 取得要建立的分類名稱
	 * @return the className 分類名稱
	 */
	public String getClassName() {
		return className;
	}


	/**
	 * 取得父分類名稱
	 * @return the parentClassName 父分類名稱
	 */
	public String getParentClassName() {
		return parentClassName;
	}

}
