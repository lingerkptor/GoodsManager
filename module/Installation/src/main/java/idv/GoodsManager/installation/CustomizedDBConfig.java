package idv.GoodsManager.installation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import idv.GoodsManager.installation.api.DB;
import idv.GoodsManager.installation.api.GetActiveDB;
import idv.lingerkptor.GoodsManager.core.DataAccess.DatabaseConfigImp;
import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;

/**
 * 客製化設定資料庫
 * 
 * @author lingerkptor
 * 
 */
public class CustomizedDBConfig {
	/**
	 * 建立資料庫設定
	 * 
	 * @param reqContext
	 */
	public static DatabaseConfigImp createDBconfig(
			idv.GoodsManager.installation.api.request.InstallationRequest reqContext) {

		String dbName = reqContext.getDatabaseName();
		String driver = reqContext.getJDBCName();
		List<DB> activedDBList = null;
		String driverUrl = null;
		try {
			activedDBList = GetActiveDB.getActivedDBList();

			driverUrl = "/lib/" + activedDBList.stream()
					.filter(db -> db.getDBName().equals(reqContext.getDatabaseName())).findFirst()
					.get().getJDBCName();
			System.out.println(driverUrl);
		} catch (NullPointerException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "資料庫尚未初始化"));
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.warn,
					"尚未上傳" + reqContext.getDatabaseName() + "資料庫檔案"));
			e.printStackTrace();
		} catch (JsonIOException | JsonSyntaxException e) {
			MessageInit.getMsgManager().deliverMessage(
					new Message(Message.Category.warn, "取得資料失敗．Message: " + e.getMessage()));
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.warn, "尚未建立自訂資料庫"));
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String url = reqContext.getURL();
		String account = reqContext.getAccount();
		String password = reqContext.getPassword();
		int maxConnection = reqContext.getMaxConnection();

		DatabaseConfigImp dbconfig = new DatabaseConfigImp(dbName, driver, driverUrl, url, account,
				password, maxConnection);
		try {
			if (dbconfig.saveConfig())
				return dbconfig;// System.out.println("設定成功");
			else
				return null; // System.out.println("設定失敗");
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

}
