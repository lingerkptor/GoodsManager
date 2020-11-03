package idv.lingerkptor.GoodsManager.core.test;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class TestObj implements Request{
	private String testString;
	private boolean testBoolean;
	private int testInt;

	TestObj() {

	}

	@Override
	public String toString() {
		return "String:" + testString + "   Boolean:" + testBoolean + "   Int:" + testInt;
	}

	@Override
	public void setAttribute(HttpSession session) {
		
	}

	@Override
	public void setAttribute(String attrName, Object obj) {
		
	}

}
