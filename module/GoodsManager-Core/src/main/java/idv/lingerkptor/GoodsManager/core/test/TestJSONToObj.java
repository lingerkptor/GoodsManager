package idv.lingerkptor.GoodsManager.core.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.AnalyzeJson;

@WebServlet("/testGson")
public class TestJSONToObj extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setAttribute("reqclass", TestObj.class);

//		req.getRequestDispatcher("/JsonToObj").include(req, resp);
//		TestObj obj = (TestObj) req.getAttribute("obj");
		PrintWriter out = resp.getWriter();
		String msg = new AnalyzeJson().<TestObj>analyze(req, TestObj.class).toString();
		System.out.println(msg);
		out.print(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6436197572975127359L;

}
