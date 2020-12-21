package idv.lingerkptor.GoodsManager.Operator.api.request;

import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class IncreateTagRequest implements Request {
	private String tagName = "";
	private String tagDescription = "";

	@Override
	public void setAttribute(HttpSession session) {
	}

	public String getTagName() {
		return tagName;
	}

	public String getTagDescription() {
		return tagDescription;
	}

}
