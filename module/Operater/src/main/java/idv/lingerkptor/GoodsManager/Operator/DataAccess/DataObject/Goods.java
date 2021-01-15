package idv.lingerkptor.GoodsManager.Operator.DataAccess.DataObject;

public class Goods {
//	GID,GNAME,L,W,H,PRICE,COUNT,C_NAME,DATE
	@SuppressWarnings("unused")
	private int id = 0;
	@SuppressWarnings("unused")
	private String gName = null;
	@SuppressWarnings("unused")
	private int price = 0, count = 0;
	@SuppressWarnings("unused")
	private String className = null;
	@SuppressWarnings("unused")
	private double date = 0;

	public Goods(int GID, String GNAME, int PRICE, int COUNT, String C_Name, double DATE) {
		this.id = GID;
		this.gName = GNAME;
		this.price = PRICE;
		this.count = COUNT;
		this.className = C_Name;
		this.date = DATE;
	}

}
