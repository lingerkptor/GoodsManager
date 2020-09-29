package idv.GoodsManager.installation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.installation.DatabaseConfigImp;
import idv.lingerkptor.util.DBOperator.ConnectPool;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;
import idv.lingerkptor.util.DBOperator.Database;

@WebServlet("/install")
public class Installation extends HttpServlet {
	private Connection conn = null;
	private DataAccessTemplate template = new DataAccessTemplate();

	private static final long serialVersionUID = -492063931427674787L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hello! World!</title>");
		out.println("</head>");
		out.println("<body>");
		out.println(
				"<h1>" + "sqlite-jdbc.jar URL is " + this.getServletContext().getRealPath("WEB-INF") + "</h1>");
		out.println("</body>");
		out.println("</html>");
		DatabaseConfigImp dbconfig = new DatabaseConfigImp(this.getServletContext().getRealPath("WEB-INF"));
		Database.setDatabaseConfig(dbconfig);
		try {
			ConnectPool.setDatabase(Database.getDatabase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn = ConnectPool.getConnection();
		out.println(
				"<h1>" + "connection is null? " + (conn == null) + "</h1>");
		ConnectPool.returnConnection(conn);

		
	}

}
