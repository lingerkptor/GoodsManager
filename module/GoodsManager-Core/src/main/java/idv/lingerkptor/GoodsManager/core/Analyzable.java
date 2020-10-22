package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletRequest;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public interface Analyzable {
	public Request analyze(HttpServletRequest req, Class<? extends Request> classT);
}
