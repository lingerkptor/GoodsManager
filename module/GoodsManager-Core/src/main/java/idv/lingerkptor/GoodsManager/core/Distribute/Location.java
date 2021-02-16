package idv.lingerkptor.GoodsManager.core.Distribute;

import java.util.List;
import java.util.Map;

/**
 * 發布地介面
 *
 * @param <T> 內容類別
 */
public interface Location<T> extends State, Packable {


    public String getName();

    public Iterable<Parcel> getParcelIterator();

    /**
     * 取得目前發布地狀態(連線狀態、或是其他狀況，需要針對狀況實作State類別)
     *
     * @return 發布狀態
     */
    public State getState();

    public Parcel getParcel(T contents);

    public void saveTemplate(String templateName, Parcel parcel);

    public String[] getTemplateNames();

    public Parcel getTemplate(String templateName);


}
