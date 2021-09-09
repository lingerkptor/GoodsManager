package idv.lingerkptor.GoodsManager.Operator.api.Distribute.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

public class SwitchLocationResponse implements Response {
    private String locationName ;
    private String errMessage ;
    private  boolean switchState ;
    public static Response failure(String name,  String message) {
        SwitchLocationResponse response = new SwitchLocationResponse();
        response.locationName = name;
        response.errMessage = message;
        return response;
    }

    public static Response success(String name, boolean switchON) {
        SwitchLocationResponse response = new SwitchLocationResponse();
        response.locationName = name;
        response.switchState = switchON;
        return response;
    }
}
