package idv.lingerkptor.GoodsManager.core.Distribute;

/**
 * 包裹的操作方法
 */
public interface Operator {

    public String getName();

    public void operator(String... args);

}