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
     * 取得目前包裹狀態
     *
     * @return 包裹狀態
     */
    public State getState();

    /**
     * 更新資料
     */
    public void update();
//
//
//
//    /**
//     * 上架
//     */
//    public void hitShelves();
//
//    /**
//     * 下架
//     */
//    public void discontinued();
}
