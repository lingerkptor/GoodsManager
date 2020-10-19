package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import idv.lingerkptor.GoodsManager.core.JsonToObj;
import idv.lingerkptor.GoodsManager.core.annotation.ClassName;
import idv.lingerkptor.GoodsManager.core.annotation.Method;

public abstract class Service extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4228208520383490591L;
	protected Object sendObj = null;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		before(req, resp);
		process(req, resp);
		after(req, resp);
	}

	public abstract void process(HttpServletRequest req, HttpServletResponse resp);

	protected void before(HttpServletRequest req, HttpServletResponse resp) {
//		try {
//			req.setCharacterEncoding("application/json; charset=UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		String method = null;
		String className = null;
		try {
			// 讀取設定的 http method
			method = getClass().getMethod("process", HttpServletRequest.class, HttpServletResponse.class)
					.getAnnotation(Method.class).value();
			// 設定要建立請求物件的類別名稱（包含完整的package）

			className = getClass().getMethod("process", HttpServletRequest.class, HttpServletResponse.class)
					.getAnnotation(ClassName.class).value();
		} catch (NoSuchMethodException e1) {
			// 有可能沒有設定請求參數，該方法的請求參數都要設定，不然match不到．
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		// 如果實際請求跟設定請求不同，就不處理
		if (!req.getMethod().equals(method))
			return;
		try {
			// 設定要建立請求類別
			req.setAttribute("class", Class.forName(className));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			JsonToObj.formJson(req);//
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立要傳過去的json物件
	 * @param req
	 * @param resp
	 */
	protected void after(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		String json = gson.toJson(sendObj);
		try {
			resp.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected final void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected final void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected final void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.service(req, resp);
	}

	@Override
	protected final void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected final void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

}
