package idv.lingerkptor.GoodsManager.core.Distribute;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 發布地介面
 *
 * @param <T> 內容類別
 */
public interface Location<T> extends State {

    /**
     * 取得發布地名稱
     * @return 發布地名稱
     */
    public String getName();

    /**
     * 取得工作清單
     * @return 工作清單
     */
    public Operator[] getJobList();

    /**
     * 取得包裹清單
     * @return 包裹清單
     */
    public Parcel<T>[] getParcelList();

    /**
     * 取得包裹清單
     * @param sortFunction 排序方法
     * @return 包裹清單
     */
    public Parcel<T>[] getParcelList(Comparator<Parcel> sortFunction);

    /**
     * 取得包裹清單
     * @param filter 包裹過濾器
     * @return 包裹清單
     */
    public Parcel<T>[] getParcelList(ParcelFilter filter);

    /**
     * 取得包裝方法清單
     * @return 包裝方法名稱
     */
    public String[] getPackableNames();

    /**
     * 取得包裝方法
     * @param templateName 包裝方法名稱
     * @return 包裝類別
     */
    public Packable<T> getPackable(String templateName);

    /**
     * 取得包裹
     * @param contents 包裹內容
     * @return 包裹
     */
    public Parcel<T> getParcel(T contents);


    /**
     * 關閉Location
     */
    public void close();

}
