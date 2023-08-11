package models.phaseVerfication;

import models.pojo.domain.RawDataDO;
import models.pojo.dto.MeterDataDTO;
import models.publicModels.DataLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/8/11
 */
public class RawDataLoader extends DataLoader {
    /**
     * 数据导入重载1，通过Excel读取，需要给定tgNo后四位（同文件命名有关）
     * 文件放置在resources根目录下
     * 注：该模式仅供调试用
     *
     * @param tgNo 台区编号后四位
     * @return 算法可用数据列表
     */
    public List<RawDataDO> importData(String tgNo) {
        return transferData(loadVoltageDataFromExcel(tgNo));
    }

    /**
     * 数据导入重载2，直接给出DTO数据，给用户使用的接口
     *
     * @param originData 原始数据列表
     * @return 算法可用数据列表
     */
    public List<RawDataDO> importData(List<MeterDataDTO> originData) {
        return transferData(originData);
    }
}
