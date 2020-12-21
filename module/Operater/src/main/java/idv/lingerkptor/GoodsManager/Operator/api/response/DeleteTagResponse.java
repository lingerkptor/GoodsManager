package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

import javax.servlet.http.HttpSession;

public enum DeleteTagResponse implements Response {
    TAGNAMEISEMPTY,//標籤名稱為空
    TAGISNOTEXIST,// 標籤不存在
    SQLEXCEPTION, SQLFILEERROR;

    private String code = this.name();


}
