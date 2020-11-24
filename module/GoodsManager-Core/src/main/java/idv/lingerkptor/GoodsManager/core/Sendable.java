package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public interface Sendable {
	public void send(Response obj, HttpServletResponse resp);
}
