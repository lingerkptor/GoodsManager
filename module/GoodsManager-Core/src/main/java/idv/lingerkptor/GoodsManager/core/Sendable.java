package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletResponse;

public interface Sendable {
	public <T> void send(T obj, HttpServletResponse resp);
}
