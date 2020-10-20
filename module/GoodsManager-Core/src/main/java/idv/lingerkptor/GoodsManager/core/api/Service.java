package idv.lingerkptor.GoodsManager.core.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import idv.lingerkptor.GoodsManager.core.Analyzable;
import idv.lingerkptor.GoodsManager.core.Sendable;
import idv.lingerkptor.GoodsManager.core.annotation.ClassName;
import idv.lingerkptor.GoodsManager.core.annotation.ContentType;

public abstract class Service extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 4228208520383490591L;
    private Analyzable analyzobj = null; // 分析請求的物件
    private Sendable sendobj = null; // 寄送的物件
    protected HttpSession session = null;

    /**
     * 工作流程(business model)
     *
     * @param requestObj 請求物件，帶有工作流程(process)所要知道的條件或是一些要處理的資料
     * @return 要送出去的物件
     */
    public abstract Object process(Object requestObj);

    /**
     * 作業流程
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected final void operater(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        // 設定請求類別                  設定寄送方式物件
        if (analyzingRequest(req, resp) && setSendObj(req, resp)) {
            //寄送(處理(分析請求))
            sendobj.send(process(analyzobj.analyze(req)), resp);
        } else
            return;
    }

    /**
     * 設定
     * @param req
     * @param resp
     * @return
     */
    private final boolean analyzingRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ContentType.RequestType contentType = getClass().getMethod("process", Object.class)
                    .getAnnotation(ContentType.class).reqType();
            if (req.getContentType().matches(contentType.getValue() + "*")) {
                analyzobj = contentType.factory();
                try {
                    Class<?> reqClass = Class // 設定要建立請求物件的類別（包含完整的package）
                            .forName(getClass().getMethod("process", Object.class).getAnnotation(ClassName.class)
                                    .reqObjClass());
                    req.setAttribute("reqclass", reqClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            }
        } catch (NoSuchMethodException e1) {
            // 有可能沒有設定請求參數，該方法的請求參數都要設定，不然match不到．
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
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
