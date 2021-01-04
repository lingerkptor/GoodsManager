package idv.lingerkptor.GoodsManager.core.DataAccess;

import java.io.File;
import java.io.FileNotFoundException;

public interface PictureWareHouse {

	/**
	 * 儲存圖片
	 * 
	 * @return 該檔案的key
	 */
	public String savePicture(File file);

	/**
	 * 取得圖片
	 * 
	 * @param key 檔案的HashCode
	 * @return 圖片檔案
	 * @exception NullPointerException 沒有這個key對應的檔案
	 * @throws FileNotFoundException 檔案已遺失
	 */
	public File getPicture(String key) throws NullPointerException, FileNotFoundException;

	/**
	 * 刪除圖片
	 * 
	 * @param key 檔案的HashCode
	 * @return 刪除是否成功
	 * @throws NullPointerException  沒有這個key對應的檔案
	 */
	public boolean deletePicture(String key) throws NullPointerException;
}
