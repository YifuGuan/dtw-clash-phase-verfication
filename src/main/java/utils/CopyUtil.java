package utils;

import models.pojo.domain.DataDO;
import models.pojo.domain.ResultDO;
import models.pojo.dto.ResultDTO;

import java.util.LinkedList;
import java.util.List;

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

    /**
     * 将结果DO数据全部拷贝到结果DTO数据对象中
     *
     * @param source 结果DO数据对象
     * @param target 结果DTO数据对象
     */
    public static void copyAllToResultDTO(ResultDO source, ResultDTO target) {
        target.setTgId(source.getTgId());
        target.setAssetNo(source.getAssetNo());
        target.setPhase(source.getPhase());
        target.setDtwA(source.getDtwA());
        target.setDtwB(source.getDtwB());
        target.setDtwC(source.getDtwC());
    }

    /**
     * 将结果DO数据对象列表转换为结果DTO数据对象列表
     *
     * @param sourceList 结果DO数据对象列表
     * @return 结果DTO数据对象列表
     */
    public static List<ResultDTO> copyAllToResultDTOList(List<ResultDO> sourceList) {
        List<ResultDTO> results = new LinkedList<>();
        for (ResultDO tuple : sourceList) {
            ResultDTO result = new ResultDTO();
            CopyUtil.copyAllToResultDTO(tuple, result);
            results.add(result);
        }
        return results;
    }
}
