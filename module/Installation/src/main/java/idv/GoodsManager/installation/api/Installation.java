package idv.GoodsManager.installation.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.util.DBOperator.DataAccessTemplate;
import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;

@WebServlet("/install")
public class Installation extends HttpServlet {
	private Connection conn = null;
	

	private static final long serialVersionUID = -492063931427674787L;

	/**
	 * @param req
	 * <p>
	 *        req.param name=
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

	}

}
