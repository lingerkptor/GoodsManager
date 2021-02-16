package idv.lingerkptor.GoodsManager.core.Distribute;

/**
 * 包裹方法，用來包裹內容物的方法．
 */
public interface Packable<T> {
    /**
     * 將內容物(商品)包裝成符合發佈地的格式
     *
     * @param contents 內容物(商品)
     * @return 以包裝好的包裹
     */
    public Parcel packing(T contents);

    /**
     * 將內容物(商品)包裝成符合發佈地的格式
     *
     * @param contents 內容物(商品)
     * @param template 參考的樣板
     * @return 以包裝好的包裹
     */
    public Parcel packing(T contents, Parcel template);

    /**
     * 將內容物(商品)包裝成符合發佈地的格式
     *
     * 重新包裝原本的包裹(替換內容物)
     *
     * @param origin 原始的包裹
     * @param contents 要替換的內容
     * @return 已包裝好的包裹
     */
    public Parcel paking(Parcel origin, T contents);

}
