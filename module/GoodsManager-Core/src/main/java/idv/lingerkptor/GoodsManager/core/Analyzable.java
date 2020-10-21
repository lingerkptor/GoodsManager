package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletRequest;

public interface Analyzable {
	public <T> T analyze(HttpServletRequest req, Class<T> classT);
}
