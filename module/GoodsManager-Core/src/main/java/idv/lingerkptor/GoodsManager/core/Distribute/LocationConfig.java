package idv.lingerkptor.GoodsManager.core.Distribute;

import idv.lingerkptor.GoodsManager.core.DataAccess.ConfigReader;

import java.util.Map;

public class LocationConfig {
    /**
     * Location開關
     */
    private boolean start = false;
    /**
     * Location 名稱，例如：露天(reten)、蝦皮(shoppee)
     */
    private String locationName;
    /**
     * Location Lib 檔案名稱
     */
    private String locationLib;
    /**
     * Location Class Name
     */
    private String locationClassName;
    private Map<String, String> locationPara;

    /**
     * 啟動開關
     *
     * @return 啟動狀態
     */
    public boolean locationSwitch() {
        this.start = !this.start;
        return this.start;
    }

    /**
     * 回傳啟動狀態
     *
     * @return 啟動狀態
     */
    public boolean isStart() {
        return this.start;
    }

    /**
     * 取得 Location Name
     *
     * @return Location  名稱，例如：露天(reten)、蝦皮(shoppee)
     */
    public String getLocationName() {
        return this.locationName;
    }

    /**
     * 取得 Location Lib 檔案名稱
     *
     * @return Location Lib 檔案名稱
     */
    public String getLocationLib() {
        return this.locationLib;
    }

    /**
     * 取得Location 類別名稱
     *
     * @return Location 類別名稱
     */
    public String getLocationClassName() {
        return this.locationClassName;
    }

    public String getParaValue(String name) {
        return this.locationPara.get(name);
    }

    public void setParaValue(String name, String value) {
        this.locationPara.put(name, value);
    }
}
