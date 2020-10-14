package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.Message.Message;

@WebServlet("/getMessages")
public class GetMessages extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 233903312735923849L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json; charset=UTF8");
		req.setAttribute("class", RequestContext.class);
		req.getRequestDispatcher("JsonToObj").include(req, resp);
		RequestContext reqContext = (RequestContext) req.getAttribute("obj");
		
	}

	public class RequestContext {
		private Message.Category category;
		private String lastMsgKey;

		RequestContext() {

		}

		public Message.Category getCategory() {
			return category;
		}

		public String getLastMsgKey() {
			return lastMsgKey;
		}

	}

}
