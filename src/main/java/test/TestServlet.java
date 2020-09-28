package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5666734745185765139L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Testbean bean = new Testbean();
		PrintWriter out = resp.getWriter();
		
		out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello! World!</title>");
        out.println("</head>");
        out.println("<body>");
       // out.println("<h1>"+"sqlite-jdbc.jar URL is "+ bean.getUrl()+"</h1>");
        out.println("<h1>"+"sqlite-jdbc.jar URL is "+ this.getServletContext().getRealPath("WEB-INF/config")+"</h1>");
        out.println("</body>");
        out.println("</html>");

	}

}
