package idv.lingerkptor.GoodsManager.core.Distribute;

/**
 * 包裹的操作方法
 */
public interface Operator {
    /**
     * 作業的進度
     */
    public enum Schedule {
        ready,//準備執行
        doing,//正在執行
        done//做完了
    }

    /**
     * 優先權
     */
    public enum Priority {
        First, Second, Third;
    }

    /**
     * 取得目前工作進度
     *
     * @return 目前工作進度
     */
    public Schedule getState();

    /**
     * 取得作業的優先權
     *
     * @return 作業的優先權
     */
    public Priority getPriority();

    /**
     * 執行作業
     *
     * @return 作業是否成功
     */
    public boolean operator();

}