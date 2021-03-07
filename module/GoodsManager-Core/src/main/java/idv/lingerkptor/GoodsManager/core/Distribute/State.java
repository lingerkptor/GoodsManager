package idv.lingerkptor.GoodsManager.core.Distribute;

/**
 * 狀態 <br/>
 * 發布地狀態、商品狀態
 */
public interface State {
    /**
     * 取得狀態名稱
     *
     * @return 狀態名
     */
    public String getState();

    /**
     * 取得Operator
     *
     * @param operatorName 作業名稱
     * @param operatorArgs 作業所需要的參數 JSON字串
     * @return Operator
     */
    public void addOperator(String operatorName, String operatorArgs);

    /**
     * 取得所有作業的清單
     *
     * @return 作業的清單
     */
    public String[] getOperatorNames();

}
