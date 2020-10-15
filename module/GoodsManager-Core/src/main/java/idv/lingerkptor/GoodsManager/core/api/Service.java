package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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
			method = getClass().getMethod("process", HttpServletRequest.class, HttpServletResponse.class)
					.getAnnotation(Method.class).value();
			className = getClass().getMethod("process", HttpServletRequest.class, HttpServletResponse.class)
					.getAnnotation(ClassName.class).value();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		System.out.println("req.getMethod():" + req.getMethod());
		System.out.println("metod:" + method);
		System.out.println("className:" + className);
		if (!req.getMethod().equals(method))
			return;
		try {
			req.setAttribute("class", Class.forName(className));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			req.getRequestDispatcher("/api/JsonToObj").include(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

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
