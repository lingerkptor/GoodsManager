package idv.lingerkptor.GoodsManager.core.test.multipart;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

public class TestMultiPartResponce implements Responce{

	@SuppressWarnings("unused")
	private boolean OK;

	private TestMultiPartResponce(boolean ok) {
		this.OK = ok;
	}

	public static TestMultiPartResponce FAILURE() {
		return new TestMultiPartResponce(false);
	}

	public static TestMultiPartResponce SUCESS() {
		return new TestMultiPartResponce(true);
	}

	@Override
	public void setAttribute(HttpSession session) {
		
	}
}
