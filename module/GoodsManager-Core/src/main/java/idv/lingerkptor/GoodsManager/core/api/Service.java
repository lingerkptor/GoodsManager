package idv.lingerkptor.GoodsManager.core.api;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Analyzable;
import idv.lingerkptor.GoodsManager.core.Sendable;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;
import idv.lingerkptor.GoodsManager.core.api.request.Request;
import idv.lingerkptor.GoodsManager.core.api.responce.Responce;

public abstract class Service extends HttpServlet {

	private static final long serialVersionUID = 4228208520383490591L;
	/**
	 * 分析請求的物件
	 */
	private Analyzable analyzobj = null;
	
	/**
	 * 寄送的方法
	 */
	private Sendable sendobj = null;
	/**
	 * 請求物件的類別
	 */
	protected Class<? extends Request> requestClass = null;

	/**
	 * 工作流程(business model)
	 *
	 * @param requestObj 請求物件，帶有工作流程(process)所要知道的條件或是一些要處理的資料
	 * @return 要送出去的物件
	 */
	public abstract Responce process(Request requestObj);

	/**
	 * 設定請求內容的的類別
	 */
	protected abstract void configRequestClass();

	/**
	 * 作業流程
	 *
	 * @param req  HttpRequest
	 * @param resp HttpResponce
	 */
	protected final void operater(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		configRequestClass();
		try {
			// 設定請求類別 設定寄送方式物件
			if (analyzingRequest(req) && setSendObj(req, resp)) {
				// 取得請求物件
				Request request = analyzobj.analyze(req, requestClass);
				// 設定伺服器端資料
				request.setAttribute(session);
				// 處理請求，取得回應物件
				Responce responceObj = process(request);
				// 設定伺服器端資料
				responceObj.setAttribute(req.getSession());
				// 寄送回應物件
				sendobj.send(responceObj, resp);
			} else
				return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 設定
	 *
	 * @param req
	 * @return 是否找到對應的分析法
	 */
	private final boolean analyzingRequest(ServletRequest req) {
		try {
			ContentType.RequestType RequestContent = this.getClass()
					.getMethod("process", Request.class).getAnnotation(ContentType.class).reqType();

			if (req.getContentType().indexOf(RequestContent.getKey()) >= 0) {
				analyzobj = RequestContent.factory();
				return true;
			}
		} catch (NoSuchMethodException e) {
			// 有可能沒有設定請求參數，該方法的請求參數都要設定，不然match不到．
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 建立要傳過去的json物件
	 *
	 * @param req
	 * @param resp
	 */
	private final boolean setSendObj(ServletRequest req, ServletResponse resp) {
		try {
			ContentType.ResponceType contentType = getClass().getMethod("process", Request.class)
					.getAnnotation(ContentType.class).respType();
			sendobj = contentType.factory();
			return true;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}

}
