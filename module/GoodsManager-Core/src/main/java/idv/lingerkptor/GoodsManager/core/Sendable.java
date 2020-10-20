package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletResponse;

public interface Sendable {
	public void send(Object obj, HttpServletResponse resp);
}
