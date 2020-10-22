package idv.lingerkptor.GoodsManager.core.test;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class TestObj2CreateObj implements InstanceCreator<TestObj> {

	@Override
	public TestObj createInstance(Type type) {
		System.out.println("createObj");
		return new TestObj();
	}

}
