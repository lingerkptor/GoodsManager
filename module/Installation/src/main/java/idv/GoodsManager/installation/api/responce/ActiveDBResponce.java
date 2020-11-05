package idv.GoodsManager.installation.api.responce;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

public class ActiveDBResponce implements Responce {
	private String DBName = null;
	private String fileName[] = null;

	@Override
	public void setAttribute(HttpSession session) {
	}

	public static Responce activeFailure(String... fileName) {
		ActiveDBResponce responce = new ActiveDBResponce();
		responce.fileName = fileName;
		return responce;
	}

	public static Responce activeSucess(String DBName) {
		ActiveDBResponce responce = new ActiveDBResponce();
		responce.DBName = DBName;
		return responce;
	}

}
