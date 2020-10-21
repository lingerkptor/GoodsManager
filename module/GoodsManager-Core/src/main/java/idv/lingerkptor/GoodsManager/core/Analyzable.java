package idv.lingerkptor.GoodsManager.core;

import javax.servlet.http.HttpServletRequest;

public interface Analyzable<T> {
	public T analyze(HttpServletRequest req, Class<?> classT);
}
