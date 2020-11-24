package idv.GoodsManager.installation.api.response;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.api.response.Response;

@SuppressWarnings("unused")
public class UploadDBFilesResponse implements Response {

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
	public static Response uploadFailure() {
		UploadDBFilesResponse Response = new UploadDBFilesResponse();
		Response.uploadSuccess = false;
		return Response;
	}

	/**
	 * 將上傳成功的資料資訊回傳
	 * 
	 * @param DBName 資料庫名稱
	 * @return 回應物件
	 */
	public static Response uploadSusscess(String DBName) {
		MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.info, "上傳資料完成"));
		UploadDBFilesResponse Response = new UploadDBFilesResponse();
		Response.DBName = DBName;
		return Response;
	}

}
