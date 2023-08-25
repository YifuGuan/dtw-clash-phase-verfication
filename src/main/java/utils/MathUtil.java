package utils;

import enums.math.PlotBoxEnum;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/18
 */
public class MathUtil {
    /**
     * 求解三个浮点数最小值，并返回该值
     *
     * @param a 第一个参数
     * @param b 第二个参数
     * @param c 第三个参数
     * @return 最小值数值
     */
    public static Double minThreeDoubleValue(Double a, Double b, Double c) {
        if (a < b && a < c) return a;
        else if (b < a && b < c) return b;
        else return c;
    }

    /**
     * 求解三个浮点数最小值，返回该值所在位置
     *
     * @param a 第一个参数
     * @param b 第二个参数
     * @param c 第三个参数
     * @return 第几个参数是最小的（1起始编号）
     */
    public static Integer minThreeDoublePos(Double a, Double b, Double c) {
        if (a < b && a < c) return 1;
        else if (b < a && b < c) return 2;
        else return 3;
    }

    /**
     * 求解箱型图四分位点
     *
     * @param source 原始数据曲线
     * @return 四分位值映射
     */
    public static HashMap<PlotBoxEnum, Double> generatePlotBox(List<Double> source) {
        // 首先进行排序
        List<Double> copyOfSource = new LinkedList<>(source);
        Collections.sort(copyOfSource);
        HashMap<PlotBoxEnum, Double> plotModel = new HashMap<>();

        // 确定箱型图上下边缘和中位点
        plotModel.put(PlotBoxEnum.MAX, copyOfSource.get(copyOfSource.size() - 1));
        plotModel.put(PlotBoxEnum.MIN, copyOfSource.get(0));
        plotModel.put(PlotBoxEnum.MID, copyOfSource.get(copyOfSource.size() / 2));

        // 确定箱型图上下四分位点
        // 首先明确四分位位置
        int quartile = source.size() / 4;
        plotModel.put(PlotBoxEnum.Q3, copyOfSource.get(copyOfSource.size() - quartile));
        plotModel.put(PlotBoxEnum.Q1, copyOfSource.get(quartile));
        return plotModel;
    }

    /**
     * 算出特定范围下，实数列表的最大值
     *
     * @param start 起始下表
     * @param end   终点下表
     * @param list  数据列表
     * @return [list(start), list(end)]范围内的最大值（含两端）
     */
    public static Double maxInList(Integer start, Integer end, List<Double> list) {
        Double max = -100000d;
        for (int i = start; i <= end; i++) {
            if (list.get(i) > max) max = list.get(i);
        }
        return max;
    }
}
