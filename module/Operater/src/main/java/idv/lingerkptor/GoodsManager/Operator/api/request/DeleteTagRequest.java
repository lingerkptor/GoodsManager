package idv.lingerkptor.GoodsManager.Operator.api.request;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

import javax.servlet.http.HttpSession;

public class DeleteTagRequest implements Request {
    /**
     * 要刪除的標籤名稱
     */
    private String tagName = "";


    /**
     * 取得要刪除的標籤名稱
     *
     * @return 標籤名稱
     */
    public String getTagName() {
        return tagName;
    }
}
