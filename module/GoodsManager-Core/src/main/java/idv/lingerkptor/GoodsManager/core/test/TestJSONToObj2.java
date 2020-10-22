package idv.lingerkptor.GoodsManager.core.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.AnalyzeJson;


@WebServlet("/testGson2")
public class TestJSONToObj2 extends HttpServlet {
	/**
	 * 
	 * TestJson <br/>
	 * {<br/>
	 * testString: String<br/>
	 * testBoolean:boolean <br/>
	 * testInt:int<br/>
	 * { <br/>
	 * testString: String<br/>
	 * testBoolean:boolean <br/>
	 * testInt:int<br/>
	 * } }
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		System.out.println("ContentType: " + req.getContentType());
		req.setAttribute("reqclass", TestObj2.class);
		
//		req.getRequestDispatcher("/api/JsonToObj").include(req, resp);
//		TestObj2 obj = (TestObj2) req.getAttribute("obj");
//		System.out.println(obj.toString());
		PrintWriter out = resp.getWriter();
		out.print(new AnalyzeJson().<TestObj2>analyze(req, TestObj2.class).toString());

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6436197572975127359L;

}
