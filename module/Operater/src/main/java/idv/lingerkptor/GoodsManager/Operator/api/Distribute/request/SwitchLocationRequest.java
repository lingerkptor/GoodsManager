package idv.lingerkptor.GoodsManager.Operator.api.Distribute.request;

import idv.lingerkptor.GoodsManager.core.api.request.Request;

public class SwitchLocationRequest implements Request {

    private String locationName;
    private boolean switchON;

    public String getLocationName() {
        return locationName;
    }

    public boolean isSwitchON() {
        return switchON;
    }
}
