package models.tgVerfication;

import models.pojo.domain.RawDataDO;
import models.pojo.domain.SeqDataDO;
import models.publicModels.DataLoader;
import utils.CopyUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author 刀氏春秋
 * @date 2023/8/11
 */
public class SeqDataLoader extends DataLoader {
    /**
     * 数据导入重载1，通过Excel读取，需给定台区编号后四位
     * 文件放置在resources文件夹下
     *
     * @param tgNo 台区编号后四位
     * @return 带时序数据列表
     */
    public List<SeqDataDO> importData(String tgNo) {
        List<RawDataDO> rawDataList = transferData(loadCurrentDataFromExcel(tgNo));

        return importData(rawDataList);
    }

    /**
     * 数据导入重载2，通过直接传入RawDataDo读取，无需指定台区编号
     * 此重载供外部直接调用
     *
     * @param source 数据源（RawDataDO数据列表）
     * @return 带时序数据列表
     */
    public List<SeqDataDO> importData(List<RawDataDO> source) {
        List<SeqDataDO> seqDataList = new LinkedList<>();
        // 数据转化，给每一个点添加时序标记
        for (RawDataDO rawDataItem : source) {
            TreeMap<Integer, Double> seqPoints = new TreeMap<>();
            SeqDataDO seqDataItem = new SeqDataDO();

            // 补充时序标记
            int index = 1;
            for (Double point : rawDataItem.getPoints()) {
                seqPoints.put(index++, point);
            }

            //复制表头数据
            CopyUtil.copyHeadersToDataTuple(rawDataItem, seqDataItem);
            seqDataItem.setSeqPoints(seqPoints);
            seqDataList.add(seqDataItem);
        }
        return seqDataList;
    }
}
