package idv.lingerkptor.GoodsManager.core.Distribute;

/**
 * Copyright ©, 2020-2021, lingerkptor
 * FileName: parcelFilter
 * Author:   lingerkptor
 * Date:     2021/3/26 下午 10:01
 * Description: 包裹過濾器
 * History:
 * <author>    <time>  <version> <desc>
 * lingerkptor  2021/3/26 下午 10:01 0 create
 */

/**
 * 包裹過濾器(篩選器)
 */
public interface ParcelFilter {

    /**
     * 篩選
     * @param parcel 被篩選的包裹
     * @return 回傳是否為對象
     */
    public boolean filter(Parcel parcel);

}
