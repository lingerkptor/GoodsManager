package idv.lingerkptor.GoodsManager.core.Distribute;

/**
 * 包裹的操作方法
 */
public interface Operator {

    public enum State {
        ready,//準備執行
        doing,//正在執行
        done//做完了
    }

    public Operator.State getState();

    public String getName();

    public boolean operator();

}