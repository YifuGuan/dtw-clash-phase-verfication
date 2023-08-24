package models.tgVerfication;

import enums.math.PlotBoxEnum;
import models.pojo.domain.FeaturesDO;
import models.pojo.domain.PeekDO;
import models.pojo.domain.RawDataDO;
import models.pojo.domain.SeqDataDO;
import utils.MathUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

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
     * 搜寻数据
     *
     * @param data 单日数据源
     * @return 单日数据峰基本信息（起始点与峰宽）
     */
    public List<PeekDO> searchPeekInSequenceCurrentData(RawDataDO data) {
        List<Double> points = data.getPoints();
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

        this.features.setPeeks(peeks);
        return peeks;
    }
}
