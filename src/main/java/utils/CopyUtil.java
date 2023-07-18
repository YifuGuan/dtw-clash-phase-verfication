package utils;

import stage.pojo.domain.DataDO;
import stage.pojo.domain.RawDataDO;

/**
 * @author 刀氏春秋
 * @date 2023/7/18
 */
public class CopyUtil {
    /**
     * 仅拷贝数据项的头部信息——包括台区号、电表资产号等
     *
     * @param source 源对数据象
     * @param target 目标数据对象
     */
    public static void copyHeaders(DataDO source, DataDO target) {
        target.setAssetNo(source.getAssetNo());
        target.setTgId(source.getTgId());
    }
}
