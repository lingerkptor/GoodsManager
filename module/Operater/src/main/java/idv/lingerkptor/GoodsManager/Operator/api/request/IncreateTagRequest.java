package idv.lingerkptor.GoodsManager.Operator.api.request;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class IncreateTagRequest implements Request {
	private String tagName = "";
	private String tagDescription = "";

	public String getTagName() {
		return tagName;
	}

	public String getTagDescription() {
		return tagDescription;
	}

}
