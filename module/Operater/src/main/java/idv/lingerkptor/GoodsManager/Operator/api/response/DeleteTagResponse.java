package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class DeleteTagResponse implements Response {
    public enum Code {
        TAGNAMEISEMPTY,//標籤名稱為空
        TAGISNOTEXIST,// 標籤不存在
        SQLEXCEPTION,// SQL例外
        SQLFILEERROR,//SQL檔案例外
        SUCCESS;//刪除成功

        public DeleteTagResponse getResponse() {
            DeleteTagResponse response = new DeleteTagResponse();
            response.code = this.name();
            return response;
        }
    }

    private DeleteTagResponse() {

    }

    private String code;


}
