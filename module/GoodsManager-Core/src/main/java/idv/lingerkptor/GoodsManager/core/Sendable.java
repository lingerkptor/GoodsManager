package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletResponse;

public interface Sendable<T> {
	public void send(T obj, HttpServletResponse resp);
}
