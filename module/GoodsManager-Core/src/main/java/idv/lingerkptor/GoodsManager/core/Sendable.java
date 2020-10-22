package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

public interface Sendable {
	public void send(Responce obj, HttpServletResponse resp);
}
