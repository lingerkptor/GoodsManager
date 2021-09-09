package idv.lingerkptor.GoodsManager.Operator.api.Distribute.response;

import idv.lingerkptor.GoodsManager.core.api.response.Response;

import java.util.Set;

public class GetLocationListResponse implements Response {
    private Object[] locationObjSet;

    public static Response success(Object... locationObjArray) {
        GetLocationListResponse response = new GetLocationListResponse();
        response.locationObjSet = locationObjArray;
        return response;
    }

    public static Object getLocationResponseObj(String name, boolean state) {
        DataBean dataBean = new DataBean();
        dataBean.locationName = name;
        dataBean.locationState = state;
        return dataBean;
    }

    public static class DataBean {
        private String locationName;
        private boolean locationState;
    }
}
