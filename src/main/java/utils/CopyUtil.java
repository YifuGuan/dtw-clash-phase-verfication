package utils;

import stage.pojo.domain.DataDO;
import stage.pojo.domain.ResultDO;

/**
 * @author 刀氏春秋
 * @date 2023/7/18
 */
public class CopyUtil {
    /**
     * 仅拷贝数据项的头部信息——包括台区号、电表资产号等到其他数据对象中
     *
     * @param source 源对数据象
     * @param target 目标数据对象
     */
    public static void copyHeadersToDataTuple(DataDO source, DataDO target) {
        target.setAssetNo(source.getAssetNo());
        target.setTgId(source.getTgId());
    }

    /**
     * 仅拷贝数据项的头部信息——包括台区号、电表资产号到结果数据对象中
     *
     * @param source 源数据对象
     * @param target 目标结果数据对象
     */
    public static void copyHeadersToResult(DataDO source, ResultDO target) {
        target.setAssetNo(source.getAssetNo());
        target.setTgId(source.getTgId());
    }
}
