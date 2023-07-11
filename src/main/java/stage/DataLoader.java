package stage;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import stage.pojo.domain.RawDataDO;
import stage.pojo.dto.MeterDataDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 * 第一阶段，用以读取数据，分Excel数据导入和DTO直接导入
 * 将其转变为利于算法使用的DO
 */
public class DataLoader {
    /**
     * 读取Excel数据
     *
     * @param tg 台区四位尾号
     * @return 电表采集原始数据
     */
    private List<MeterDataDTO> loadDataFromExcel(String tg) {
        // 首先读取出originData，再将其转化为rawData
        List<MeterDataDTO> originData = new LinkedList<>();

        // 通过EasyExcel读取数据表
        System.out.println("开始加载");
        try {
            EasyExcel.read("src/main/resources/voltage-" + tg +".xlsx", MeterDataDTO.class, new ReadListener<MeterDataDTO>() {
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
     * 将DTO类型原始数据转变为算法可用的DO类型数据
     *
     * @param originData 原始数据列表
     * @return 算法用数据列表
     */
    private List<RawDataDO> transferData(List<MeterDataDTO> originData) {
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
                    // 反射式调用getter获取数据点
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

    /**
     * 数据导入重载1，通过Excel读取，需要给定tgNo后四位（同文件命名有关）
     * 文件放置在resources根目录下
     * 注：该模式仅供调试用
     *
     * @param tgNo 台区编号后四位
     * @return 算法可用数据列表
     */
    public List<RawDataDO> importData(String tgNo) {
        return transferData(loadDataFromExcel(tgNo));
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
