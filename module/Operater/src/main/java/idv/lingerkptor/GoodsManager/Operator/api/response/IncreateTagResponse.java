package idv.lingerkptor.GoodsManager.Operator.api.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class IncreateTagResponse implements Response {
    public enum Code {
        SQLException, // SQLException 例外發生
        SQLFILEERROR, // SQLFile出錯
        OTHEREXCEPTION, // 其他例外
        TAGISEXIST, // TAG已經存在
        TAGNAMEISEMPTY, // 標籤名稱為空
        INCREATEFAILURE, // 標籤建立失敗
        SUCCESS;// 建立成功

        public IncreateTagResponse getResponse() {
            IncreateTagResponse response = new IncreateTagResponse();
            response.code = this.name();
            return response;
        }
    }

    @SuppressWarnings("unused")
    private String code;

}
