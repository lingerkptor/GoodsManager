package idv.GoodsManager.installation.api.responce;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

@SuppressWarnings("unused")
public class UploadDBFilesResponce implements Responce {

	private boolean uploadSuccess = true;
	private String DBName;

	@Override
	public void setAttribute(HttpSession session) {

	}

	/**
	 * 通知上傳失敗
	 * 
	 * @return 回應物件
	 */
	public static Responce uploadFailure() {
		UploadDBFilesResponce responce = new UploadDBFilesResponce();
		responce.uploadSuccess = false;
		return responce;
	}

	/**
	 * 將上傳成功的資料資訊回傳
	 * 
	 * @param DBName 資料庫名稱
	 * @return 回應物件
	 */
	public static Responce uploadSusscess(String DBName) {
		MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.info, "上傳資料完成"));
		UploadDBFilesResponce responce = new UploadDBFilesResponce();
		responce.DBName = DBName;
		return responce;
	}

}
