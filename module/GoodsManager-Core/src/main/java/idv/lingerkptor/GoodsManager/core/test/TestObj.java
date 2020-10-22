package idv.lingerkptor.GoodsManager.core.test;

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

}
