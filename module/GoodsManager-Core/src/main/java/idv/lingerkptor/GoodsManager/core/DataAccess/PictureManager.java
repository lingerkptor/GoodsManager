package idv.lingerkptor.GoodsManager.core.DataAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import idv.lingerkptor.GoodsManager.core.Listener.MessageInit;
import idv.lingerkptor.GoodsManager.core.Message.Message;

public class PictureManager implements PictureWareHouse {
	private PictureWareHouse warehouse;
	private static PictureManager manager = null;

	private PictureManager() {

	}

	public static PictureManager getPictureManager() {
		if (manager == null)
			manager = new PictureManager();
		return manager;
	}

	/**
	 * 註冊圖片倉庫
	 * 
	 * @param whClassName     倉庫類別名稱
	 * @param photoConfigAddr 倉庫設定檔位址
	 */
	public void registerPWH(String whClassName, String photoConfigAddr) {
		try {
			manager.warehouse = (PictureWareHouse) Class.forName(whClassName)
					.getConstructor(new Class[] { String.class }).newInstance(photoConfigAddr);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String savePicture(File file) {
		if (warehouse == null)
			throw new RuntimeException("Not Registation, registering Picture Warehouse ,please.");
		return warehouse.savePicture(file);
	}

	@Override
	public File getPicture(String key) {
		try {
			return warehouse.getPicture(key);
		} catch (NullPointerException e) {
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err, "查無此檔案"));
		} catch (FileNotFoundException e) {
			MessageInit.getMsgManager()
					.deliverMessage(new Message(Message.Category.err, "檔案遺失，請注意圖片倉庫有問題"));
		}
		return null;
	}

	@Override
	public boolean deletePicture(String key) {
		try {
			return warehouse.deletePicture(key);
		} catch (NullPointerException e) {
			MessageInit.getMsgManager().deliverMessage(new Message(Message.Category.err, "查無此檔案"));
			return true;
		}
	}
}
