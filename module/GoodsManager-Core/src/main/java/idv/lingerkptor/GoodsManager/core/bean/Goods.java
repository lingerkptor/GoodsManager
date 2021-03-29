package idv.lingerkptor.GoodsManager.core.bean;

import java.util.LinkedList;
import java.util.List;

public class Goods {
	@SuppressWarnings("unused")
	private int id = 0;
	@SuppressWarnings("unused")
	private String goodsName = null;
	@SuppressWarnings("unused")
	private int price = 0, count = 0;
	@SuppressWarnings("unused")
	private String className = null;
	@SuppressWarnings("unused")
	private long date = 0;
	@SuppressWarnings("unused")
	private double L = 0, W = 0, H = 0;
	private List<String> tags = null, picturesKey = null;

	public Goods(int GID, String goodsName, int PRICE, int COUNT, String C_Name, long DATE) {
		this.id = GID;
		this.goodsName = goodsName;
		this.price = PRICE;
		this.count = COUNT;
		this.className = C_Name;
		this.date = DATE;
	}

	public Goods(int GID, String goodsName, int PRICE, int COUNT, String C_Name, long DATE,
			double L, double W, double H) {
		this(GID, goodsName, PRICE, COUNT, C_Name, DATE);
		this.L = L;
		this.W = W;
		this.H = H;
		tags = new LinkedList<String>();
		picturesKey = new LinkedList<String>();
	}

	public void appendTag(String tag) {
		this.tags.add(tag);
	}

	public void appendPicKey(String key) {
		this.picturesKey.add(key);
	}

}
