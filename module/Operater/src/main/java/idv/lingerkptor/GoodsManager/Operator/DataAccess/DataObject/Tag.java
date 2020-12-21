package idv.lingerkptor.GoodsManager.Operator.DataAccess.DataObject;

public class Tag {
	@SuppressWarnings("unused")
	private String tagName = "";
	@SuppressWarnings("unused")
	private String tagDescription = "";
	private int count = 0;

	@SuppressWarnings("unused")
	private Tag() {
	}

	public Tag(String tagName, String tagDescription, int count) {
		this.tagName = tagName;
		this.tagDescription = tagDescription;
		this.count = count;
	}
}
