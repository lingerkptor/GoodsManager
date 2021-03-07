package idv.lingerkptor.GoodsManager.core.Distribute;

import java.util.List;
import java.util.Map;

/**
 * 發布地介面
 *
 * @param <T> 內容類別
 */
public interface Location<T> extends State {

    public String getName();

    public List<Operator> getJobList();

    public Iterable<Parcel> getParcelIterator();

    public boolean addParcel(Packable packable, T contents);

    public String[] getPackableNames();

    public Packable getPackable(String packableName);

    public Parcel getParcel(T contents);

    public Parcel getTemplate(String templateName);

    public void close();

}
