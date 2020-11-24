package idv.GoodsManager.installation.api.response;

import java.util.List;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class GetActiveDBResponse implements Response {
	@SuppressWarnings("unused")
	private List<String> activedDBList = null;

	@Override
	public void setAttribute(HttpSession session) {

	}

	public static Response readActivedDBListFail() {
		return new GetActiveDBResponse();
	}

	public static Response sendActivedDBList(List<String> list) {
		GetActiveDBResponse Response = new GetActiveDBResponse();
		Response.activedDBList = list;
		return Response;
	}

}
