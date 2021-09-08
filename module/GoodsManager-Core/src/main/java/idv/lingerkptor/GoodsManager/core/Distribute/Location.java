package idv.lingerkptor.GoodsManager.core.Distribute;

import idv.lingerkptor.GoodsManager.core.Distribute.bean.Operator;
import idv.lingerkptor.GoodsManager.core.Distribute.bean.Packable;
import idv.lingerkptor.GoodsManager.core.Distribute.bean.Parcel;
import idv.lingerkptor.GoodsManager.core.bean.Goods;

import java.util.Comparator;
import java.util.Map;

/**
 * 發布地介面
 */
public interface Location {

    /**
     * 取得發布地名稱
     *
     * @return 發布地名稱
     */
    public String getName();



    /**
     * 加入商品
     *
     * @param goods 商品
     */
    public boolean addParcel(Parcel goods);

    /**
     * 移除商品
     *
     * @param goods 商品
     */
    public void removeGoods(Goods goods);

    /**
     * 修改包裹
     *
     * @param parcel 已修改的包裹
     */
    public void modifyParcel(Parcel parcel);

    /**
     * 取得包裹
     *
     * @param contents 包裹內容
     * @return 包裹
     */
    public Parcel getParcel(Goods contents);

    /**
     * 取得包裹清單
     *
     * @return 包裹清單
     */
    public Parcel[] getParcelList();

    /**
     * 取得包裹清單
     *
     * @param sortFunction 排序方法
     * @return 包裹清單
     */
    public Parcel[] getParcelList(Comparator<Parcel> sortFunction);

    /**
     * 取得包裹清單
     *
     * @param filter 包裹過濾器
     * @return 包裹清單
     */
    public Parcel[] getParcelList(ParcelFilter filter);

    /**
     * 取得工作清單
     *
     * @return 工作清單
     */
    public Operator[] getTaskList();

    /**
     * 取得包裝方法清單
     *
     * @return 包裝方法名稱
     */
    public String[] getPackableNames();

    /**
     * 製作包裹器
     * @param packableName 包裝器名稱
     * @param paraMap 包裝器所需要的參數
     * @return 樣板實例
     */
    public Packable makePackable(String packableName, Map<String,Object> paraMap);

    /**
     * 取得設定
     *
     * @return 設定
     */
    public LocationConfig getConfig();


    /**
     * 關閉Location
     */
    public void close();

}
