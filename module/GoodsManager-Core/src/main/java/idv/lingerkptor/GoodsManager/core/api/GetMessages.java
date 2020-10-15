package idv.lingerkptor.GoodsManager.core.api;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.Message.Message;
import idv.lingerkptor.GoodsManager.core.annotation.ClassName;
import idv.lingerkptor.GoodsManager.core.annotation.Method;

//json封包
//{
//	category:err  info warn
//}
/**
 * 取得訊息
 * 
 * @author lingerkptor
 *
 */
@WebServlet("/api/getMessages")
public class GetMessages extends Service {
	/**
	 * 
	 */
	private static final long serialVersionUID = 233903312735923849L;

	@Override
	@Method(value ="POST") // 這一行一定要
	@ClassName(value = "idv.lingerkptor.GoodsManager.core.api.GetMessages$RequestContext") // 這一行一定要
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println(RequestContext.class.getName());
		RequestContext reqContext = (RequestContext) req.getAttribute("obj");
		System.out.println("reqContext:" + reqContext.getCategory());
		super.sendObj = req.getSession().getAttribute(reqContext.getCategory());
	}

	protected class RequestContext {
		private Message.Category category;

		RequestContext() {
		}

		public String getCategory() {
			return category.toString();
		}
	}

}
