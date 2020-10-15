package idv.lingerkptor.GoodsManager.core.api.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestJSONToObj extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setAttribute("class", TestObj.class);
		req.getRequestDispatcher("/JsonToObj").include(req, resp);
		TestObj obj = (TestObj) req.getAttribute("obj");
		PrintWriter out = resp.getWriter();
		out.print(obj.toString());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6436197572975127359L;

}
