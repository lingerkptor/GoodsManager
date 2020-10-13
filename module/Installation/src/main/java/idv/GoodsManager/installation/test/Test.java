package idv.GoodsManager.installation.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import idv.lingerkptor.GoodsManager.core.DataAccess.DataAccessCore;
import idv.lingerkptor.util.DBOperator.DataAccessTemplate;
import idv.lingerkptor.util.DBOperator.PreparedStatementCreator;

@WebServlet("/test")
public class Test extends HttpServlet {

	DataAccessTemplate template = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -264198190769532469L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		out.println("<html><body>");
//		out.println("資料庫狀態：" + DataAccessCore.getState());
//		System.out.print("資料庫狀態：" + DataAccessCore.getState());
//		PreparedStatementCreator createtable = new CreateTestTable();
//		System.out.println("取得資料庫存取樣板");
//		out.print("取得資料庫存取樣板");
//		template = DataAccessCore.getSQLTemplate();
//		out.print("新增資料表");
//		System.out.println("新增資料表");
//		template.update(createtable);
//
//		out.print("新增資料");
//		System.out.println("新增資料");
//		AddDataSQL addData = new AddDataSQL();
//		
//		addData.addData("test", 555);
//		addData.addData("test2", 666);
//		template.update(addData);
//		out.print("查詢資料");
//		System.out.println("查詢資料");
//		QueryDataSQL queryData = new QueryDataSQL();
//		QueryDataResult checkData = new QueryDataResult();
//		checkData.addCheckData("test", 555);
//		checkData.addCheckData("test2", 666);
//		template.query(queryData, checkData);
//
//		
//		out.println("<h1> 資料數是否正確?　" + checkData.checkSize());
//		out.println("</h1><h1> 資料是否正確?　" + checkData.checkData() + "</h1>");

		out.println("</body></html>");

	}

}
