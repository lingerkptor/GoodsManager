package idv.lingerkptor.GoodsManager.core.test.multipart;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class TestMultiPartResponse implements Response{

	@SuppressWarnings("unused")
	private boolean OK;

	private TestMultiPartResponse(boolean ok) {
		this.OK = ok;
	}

	public static TestMultiPartResponse FAILURE() {
		return new TestMultiPartResponse(false);
	}

	public static TestMultiPartResponse SUCESS() {
		return new TestMultiPartResponse(true);
	}

	@Override
	public void setAttribute(HttpSession session) {
		
	}
}
