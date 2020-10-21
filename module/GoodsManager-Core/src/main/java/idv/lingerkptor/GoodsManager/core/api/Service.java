package idv.lingerkptor.GoodsManager.core.api;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Analyzable;
import idv.lingerkptor.GoodsManager.core.Sendable;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;

public abstract class Service<T1,T2> extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 4228208520383490591L;
    /**
     * 分析請求的物件
     */
    private Analyzable<T1> analyzobj = null;
    /**
     * 寄送的方法
     */
    private Sendable<T2> sendobj = null;
    protected HttpSession session = null;
    protected Class<T1> requestObj =null;

    /**
     * 工作流程(business model)
     *
     * @param requestObj 請求物件，帶有工作流程(process)所要知道的條件或是一些要處理的資料
     * @return 要送出去的物件
     */
    public abstract T2 process(T1 requestObj);

    /**
     * 設定請求內容的的類別
     */
    protected abstract void configRequestClass();
    /**
     * 作業流程
     * @param req HttpRequest
     * @param resp HttpResponce
     */
    protected final void operater(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        configRequestClass();
        try {
        // 設定請求類別                  設定寄送方式物件
        if (analyzingRequest(req) && setSendObj(req, resp)) {
            //寄送(處理(分析請求))
            sendobj.send(process(analyzobj.analyze(req, requestObj)), resp);
        } else
            return;
        }catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * 設定
     * @param req
     * @return 是否找到對應的分析法
     */
    private final boolean analyzingRequest(HttpServletRequest req) {
        try {
        	// 泛型參數可使用Object.class
            ContentType.RequestType contentType = this.getClass().getMethod("process", Object.class)
                    .getAnnotation(ContentType.class).reqType();
            if (req.getContentType().matches(contentType.getKey() + "*")) {

                analyzobj = contentType.<T1>factory();
//                try {
//                    Class<?> reqClass = Class // 設定要建立請求物件的類別（包含完整的package）
//                            .forName(getClass().getMethod("process", Object.class).getAnnotation(ClassName.class)
//                                    .reqObjClass());
//                    req.setAttribute("reqclass", reqClass);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
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
    private final boolean setSendObj(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ContentType.ResponceType contentType = getClass().getMethod("process", Object.class)
                    .getAnnotation(ContentType.class).respType();
            sendobj = contentType.<T2>factory();
            return true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

}
