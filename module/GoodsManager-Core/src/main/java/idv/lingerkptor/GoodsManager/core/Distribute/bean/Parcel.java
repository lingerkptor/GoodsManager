package idv.lingerkptor.GoodsManager.core.Distribute.bean;

import idv.lingerkptor.GoodsManager.core.Distribute.Location;
import idv.lingerkptor.GoodsManager.core.bean.Goods;

/**
 * 包裹，已經將內容物包裹起來．
 */
public interface Parcel {

    /**
     * 包裹狀態
     */
    public enum STATUS {
        OnMarket,//上架中
        downMarket,//下架中
        sellOut,//已售完
        other //其他狀態
    }

    /**
     * 取得目前包裹狀態
     * @return 包裹狀態
     */
    public STATUS getStatus();

    /**
     * 取得內容物
     *
     * @return 內容物
     */
    public Goods getContents();

    /**
     * 取得發布地
     *
     * @return 發布地
     */
    public Location getLocation();

    /**
     * 取得參數名稱清單
     *
     * @return 參數名稱清單
     */
    public String[] getParameterNames();

    /**
     * 取得參數值
     *
     * @param parameterName 參數名稱
     * @return 參數值
     */
    public String getParameterValue(String parameterName);

}