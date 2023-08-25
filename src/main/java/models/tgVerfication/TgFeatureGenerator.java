package models.tgVerfication;

import enums.math.PlotBoxEnum;
import models.pojo.domain.FeaturesDO;
import models.pojo.domain.PeekDO;
import models.pojo.domain.RawDataDO;
import utils.MathUtil;

import java.util.*;

/**
 * @author 刀氏春秋
 * @date 2023/8/14
 * 根据曲线数据生成台区特征Map
 * 输入一段带时序序列
 * 导出特征向量
 */
public class TgFeatureGenerator {
    private final Double EXTREME_SENSITIVITY = 3.0;
    private FeaturesDO features = new FeaturesDO();

    /**
     * 搜寻数据峰并记录基本信息
     *
     * @param data 单日数据源
     * @return 单日数据峰基本信息（起始点与峰宽）
     */
    private List<PeekDO> searchPeekInSingleDayCurrentData(RawDataDO data, Integer dayIndex) {
        List<Double> points = data.getPoints().get(dayIndex);
        List<PeekDO> peeks = new LinkedList<>();

        // 通过箱型图（四分位）模型确定划分阈值
        HashMap<PlotBoxEnum, Double> plotModel = MathUtil.generatePlotBox(points);
        Double threshold = plotModel.get(PlotBoxEnum.Q3)
                + EXTREME_SENSITIVITY * (plotModel.get(PlotBoxEnum.Q3) - plotModel.get(PlotBoxEnum.Q1));

        // 滑动窗口寻找峰
        int left = 0, right = 0;
        while (left < points.size()) {
            // 右边界扩散条件——当前点值高于阈值
            while (right < points.size() && points.get(right) > threshold) {
                System.out.println("右移，right=" + right);
                right++;
            }
            // 仅当有窗口扩散时才认为存在峰
            if (right > left) {
                peeks.add(new PeekDO(left, right - left - 1));
            }
            System.out.println("左移，left=" + left);
            left = ++right;
        }

        return peeks;
    }

    /**
     * 计算峰度（左斜率）
     *
     * @param data  数据列表
     * @param peeks 日-峰列表映射
     */
    private void calculatePeekKurtosis(RawDataDO data, Map<Integer, List<PeekDO>> peeks) {
        for (Integer day : peeks.keySet()) {
            // 遍历单天的所有peek
            for (PeekDO peek : peeks.get(day)) {
                // 获取当日数据点，用以计算左斜率
                List<Double> dayPoints = data.getPoints().get(day);
                Double maxValue = MathUtil.maxInList(peek.getStartIndex(),
                        peek.getStartIndex() + peek.getPeekWidth(), dayPoints);
                Integer maxIndex = dayPoints.indexOf(maxValue);
                peek.setKurtosis(maxValue / (maxIndex - peek.getStartIndex()));
            }
        }
    }

    /**
     * 获取所有峰数据
     *
     * @param data 单相量测数据
     */
    private void searchPeekInAllDayCurrentData(RawDataDO data) {
        // 遍历所有日数据，算出峰值
        for (int day = 0; day < data.getPoints().size(); day++) {
            this.features.appendDayPeeks(searchPeekInSingleDayCurrentData(data, day), day);
        }
    }

    /**
     * 生成特征DO数据对象
     *
     * @param data 单相量测数据
     * @return 特征数据对象
     */
    public FeaturesDO generateFeature(RawDataDO data) {
        searchPeekInAllDayCurrentData(data);
        calculatePeekKurtosis(data, this.features.getPeeks());
        return this.features;
    }
}
