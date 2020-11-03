package idv.lingerkptor.GoodsManager.core.api.request;

import javax.servlet.http.HttpSession;

public interface Request {
	public void setAttribute(HttpSession session) ;
	public void setAttribute(String attrName, Object obj);
}
