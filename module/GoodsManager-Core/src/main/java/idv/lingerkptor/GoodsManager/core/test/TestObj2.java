package idv.lingerkptor.GoodsManager.core.test;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class TestObj2 implements Request{
	private String testString = "";
	private boolean testBoolean = false;
	private int testInt = 0;
	private TestObj obj = null;

	public TestObj2() {

	}

	@Override
	public String toString() {
		String objtoString;
		if (obj == null)
			objtoString = "null";
		else
			objtoString = obj.toString();
		return " String:" + testString + " ,  Boolean:" + testBoolean + " ,   Int:" + testInt + ", obj " + objtoString;
	}

	@Override
	public void setAttribute(HttpSession session) {
		// TODO Auto-generated method stub
		
	}

}
