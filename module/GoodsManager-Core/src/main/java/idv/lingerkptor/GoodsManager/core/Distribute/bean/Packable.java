package idv.lingerkptor.GoodsManager.core.Distribute.bean;

import idv.lingerkptor.GoodsManager.core.Distribute.bean.Parcel;
import idv.lingerkptor.GoodsManager.core.bean.Goods;

/**
 * 包裹方法，用來包裹內容物的方法．將內容物(商品)包裝成符合發佈地的格式
 */
public interface Packable {
    /**
     * 將內容物(商品)包裝成符合發佈地的格式
     *
     * @return 以包裝好的包裹
     */
    public Parcel packing(Goods content);
}
