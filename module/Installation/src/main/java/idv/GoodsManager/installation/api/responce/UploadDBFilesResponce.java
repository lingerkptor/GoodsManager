package idv.GoodsManager.installation.api.responce;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

public class UploadDBFilesResponce implements Responce {

	private String DBName;
	private String SQLName;
	private String JDBCName;

	@Override
	public void setAttribute(HttpSession session) {

	}

	/**
	 * 通知上傳失敗
	 * 
	 * @return 回應物件
	 */
	public static Responce uploadFailure() {
		MessageInit.getMsgManager()
				.deliverMessage(new Message(Message.Category.warn, "資料有錯誤，請重新上傳"));
		return null;
	}

	/**
	 * 將上傳成功的資料資訊回傳
	 * 
	 * @param DBName   資料庫名稱
	 * @param JDBCName JDBC名稱
	 * @param SQLName  SQLZIP檔名
	 * @return 回應物件
	 */
	public static Responce uploadSusscess(String DBName, String JDBCName, String SQLName) {
		MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.info, "上傳資料完成"));
		UploadDBFilesResponce responce = new UploadDBFilesResponce();
		responce.DBName = DBName;
		responce.JDBCName = JDBCName;
		responce.SQLName = SQLName;
		return responce;
	}

}
