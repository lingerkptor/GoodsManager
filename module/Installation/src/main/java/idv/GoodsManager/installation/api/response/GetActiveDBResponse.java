package idv.GoodsManager.installation.api.response;

import java.util.List;


import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class GetActiveDBResponse implements Response {
	@SuppressWarnings("unused")
	private List<String> activedDBList = null;

	public static Response readActivedDBListFail() {
		return new GetActiveDBResponse();
	}

	public static Response sendActivedDBList(List<String> list) {
		GetActiveDBResponse Response = new GetActiveDBResponse();
		Response.activedDBList = list;
		return Response;
	}

}
