package models.publicModels;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import models.pojo.domain.RawDataDO;
import models.pojo.dto.MeterDataDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 * 第一阶段，用以读取数据，分Excel数据导入和DTO直接导入
 * 输入：MeterDataDTO数据列表（实际）/TgNo后四位（调试）
 * 输出：RawDataDO数据列表
 * 将其转变为利于算法使用的DO
 */
public class DataLoader {
    private List<MeterDataDTO> loadDataFromExcel(String filename) {
        List<MeterDataDTO> originData = new LinkedList<>();

        System.out.println("开始加载");
        try {
            EasyExcel.read(filename, MeterDataDTO.class, new ReadListener<MeterDataDTO>() {
                @Override
                public void invoke(MeterDataDTO tuple, AnalysisContext analysisContext) {
                    // 只导入总表和单相表
                    if (tuple.getWiringMode().equals("1") || tuple.getUserMode().equals("2"))
                        originData.add(tuple);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    System.out.printf("file reading completed, %d recordings.%n", originData.size());
                }
            }).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return originData;
    }

    /**
     * 读取电压数据
     *
     * @param tg 台区四位尾号
     * @return 电表电压采集原始数据
     */
    protected List<MeterDataDTO> loadVoltageDataFromExcel(String tg) {
        // 首先读取出originData，再将其转化为rawData
        return loadDataFromExcel("src/main/resources/voltage-" + tg + ".xlsx");
    }

    /**
     * 读取电流数据
     *
     * @param tg 台区四位尾号
     * @return 电表电流采集原始数据
     */
    protected List<MeterDataDTO> loadCurrentDataFromExcel(String tg) {
        return loadDataFromExcel("src/main/resources/current-" + tg + ".xlsx");
    }

    /**
     * 将DTO类型原始数据转变为算法可用的DO类型数据
     *
     * @param originData 原始数据列表
     * @return 算法用数据列表
     */
    protected List<RawDataDO> transferData(List<MeterDataDTO> originData) {
        // 将原始数据转化为生数据
        List<RawDataDO> rawData = new LinkedList<>();

        // 采用反射方式将数据全部读入到rawData的列表当中
        for (MeterDataDTO tuple : originData) {
            // 生数据导入，先读入基本属性数据
            RawDataDO rawDataDO = new RawDataDO();
            rawDataDO.setTgId(tuple.getTgNo());
            rawDataDO.setAssetNo(tuple.getAssetNo());

            // 利用反射读取测量数据
            List<Double> points = new LinkedList<>();
            try {
                for (int index = 1; index <= 96; index++) {
                    // 反射式调用getter获取数据点，注，该步可能引入空值点
                    points.add((Double) MeterDataDTO.class.getDeclaredMethod("getI" + index).invoke(tuple));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            rawDataDO.setPoints(points);
            rawData.add(rawDataDO);
        }

        return rawData;
    }
}
