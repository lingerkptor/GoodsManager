package idv.lingerkptor.GoodsManager.core.Distribute;

/**
 * 包裹，已經將內容物包裹起來．
 *
 * @param <T> 內容物類別
 */
public interface Parcel<T> extends State {
    /**
     * 取得內容物
     *
     * @return 內容物
     */
    public T getContents();

    /**
     * 取得發布地
     *
     * @return 發布地
     */
    public Location<T> getLocation();

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