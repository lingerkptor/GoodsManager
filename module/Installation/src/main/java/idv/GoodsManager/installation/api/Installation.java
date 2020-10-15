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
	 *            json 資料<br/>
	 *            { <br/>
	 *            customized:boolean, 是否客製化資料庫 <br/>
	 *            databaseName: String 資料庫名稱 <br/>
	 *            JDBC:String JDBC名稱    <br/>
	 *            URL:String 資料庫URL<br/>
	 *            account: String 資料庫帳號<br/>
	 *            password:String 資料庫密碼 <br/>
	 *            maxConnection:Int 最大連線數 <br/>
	 *            }
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		/**
		 * 資料庫設定相關
		 * 
		 */
		// 設定需要轉換的物件
		 req.setAttribute("class", Boolean.class);
		req.getRequestDispatcher("/JsonToObj").include(req, resp);
		// 選擇自定義資料庫或預設資料庫(SQLite) (還沒做)

		// 如果是自定義資料庫，設定資料庫 (還沒做)
		// 設定資料庫名稱
		// 確認上傳JDBC、SQL檔(ZIP)(事先上傳)
		// 設定資料庫相關訊息(URL、帳號、密碼)
		// 測試連線
		// 建立資料表 (還沒做)
		// 測試相關SQL (還沒做)

	}
	protected interface reqContext {
		
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		super.service(req, resp);
	}

}
