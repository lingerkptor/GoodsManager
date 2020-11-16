package idv.GoodsManager.installation.api.responce;

import java.util.List;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

public class GetActiveDBResponce implements Responce {
	@SuppressWarnings("unused")
	private List<String> activedDBList = null;

	@Override
	public void setAttribute(HttpSession session) {

	}

	public static Responce readActivedDBListFail() {
		return new GetActiveDBResponce();
	}

	public static Responce sendActivedDBList(List<String> list) {
		GetActiveDBResponce responce = new GetActiveDBResponce();
		responce.activedDBList = list;
		return responce;
	}

}
