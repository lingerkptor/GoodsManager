package idv.lingerkptor.GoodsManager.Operator.api.request;

import java.text.SimpleDateFormat;
import java.util.Date;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class ModifyGoodsRequest implements Request {
	private int id = 0;
	private String goodsName = "";
	private double L = 0;
	private double W = 0;
	private double H = 0;
	private int price = 0;
	private int count = 0;
	private String className = "";
	private String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	private String[] tags = null;
	private String[] picturesKey = null;

	/**
	 * 商品名稱
	 * 
	 * @return the gName 商品名稱
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 取得商品長度
	 * 
	 * @return the l 商品長度
	 */
	public double getL() {
		return L;
	}

	/**
	 * 取得商品寬度
	 * 
	 * @return the w 商品寬度
	 */
	public double getW() {
		return W;
	}

	/**
	 * 取得商品厚度(高度)
	 * 
	 * @return the h 商品厚度(高度)
	 */
	public double getH() {
		return H;
	}

	/**
	 * 取得商品數量
	 * 
	 * @return the price 商品數量
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 取得商品價格
	 * 
	 * @return the price 商品價格
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * 取得商品分類
	 * 
	 * @return the className 商品分類
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 取得標籤清單
	 * 
	 * @return 標籤清單
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * 取得圖片碼清單
	 * 
	 * @return 圖片碼清單
	 */
	public String[] getPictureKey() {
		return this.picturesKey;
	}

	/**
	 * 取得商品建立時間
	 * 
	 * @return 商品建立時間
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 取得商品GID
	 * 
	 * @return 商品GID
	 */
	public int getId() {
		return id;
	}
}
