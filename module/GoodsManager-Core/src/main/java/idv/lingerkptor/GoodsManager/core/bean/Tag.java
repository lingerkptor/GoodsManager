package idv.lingerkptor.GoodsManager.core.bean;

public class Tag {
	@SuppressWarnings("unused")
	private String tagName = "";
	@SuppressWarnings("unused")
	private String tagDescription = "";
	@SuppressWarnings("unused")
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
