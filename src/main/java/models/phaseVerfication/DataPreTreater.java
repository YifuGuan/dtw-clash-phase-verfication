package models.phaseVerfication;

import models.pojo.domain.RawDataDO;
import utils.CopyUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 * 第二阶段，数据预处理，为提高DTW算法的实际效用，需要对原始数据做初步处理
 * 输入：RawDataDO数据列表
 * 输出：InputDataDO数据列表
 * -------------------------------------
 * 处理1：去空值处理，空值数据相当于没有
 * -------------------------------------
 * 处理2：去均值处理，由于电压存在压降，所以即便是处在同一相上的电表电压也会按照梯度逐步下降，
 * 而这种下降会对DTW距离计算产生影响（已数学证明），故采用去均值法，把重点放在电压的波动上
 */
public class DataPreTreater {
    /**
     * 去空值处理
     *
     * @param origin 未处理数据列表
     * @return 处理后数据列表
     */
    private List<RawDataDO> ridNullPoint(List<RawDataDO> origin) {
        List<RawDataDO> results = new LinkedList<>();
        for (RawDataDO tuple : origin) {
            RawDataDO result = new RawDataDO();

            // 仅引入非空数据点
            for (Double point : tuple.getPoints()) {
                if (point != null) result.appendPoint(point);
            }

            // 将处理后数据添加到结果列表中
            CopyUtil.copyHeadersToDataTuple(tuple, result);
            results.add(result);
        }
        return results;
    }

    /**
     * 计算出点列的平均值
     *
     * @param points 电压点列数据
     * @return 电压平均值
     */
    private Double getAverageOfDoubleList(List<Double> points) {
        Double average = 0d;
        for (Double point : points) {
            average += point;
        }
        return average / points.size();
    }

    /**
     * 去均值处理
     *
     * @param origin 原始数据列表
     * @return 处理后数据列表
     */
    private List<RawDataDO> ridAverage(List<RawDataDO> origin) {
        List<RawDataDO> results = new LinkedList<>();
        for (RawDataDO tuple : origin) {
            RawDataDO result = new RawDataDO();

            // 计算均值
            Double average = getAverageOfDoubleList(tuple.getPoints());
            for (Double point : tuple.getPoints()) {
                // 去均值
                result.appendPoint(point - average);
            }

            // 将处理后数据添加到结果列表中
            CopyUtil.copyHeadersToDataTuple(tuple, result);
            results.add(result);
        }
        return results;
    }

    /**
     * 对外策略接口1：仅去空值
     *
     * @param origin 原始数据列表
     * @return 处理后数据列表
     */
    public List<RawDataDO> ridNullPointOnly(List<RawDataDO> origin) {
        return ridNullPoint(origin);
    }

    /**
     * 对外策略接口2：去空值和均值
     *
     * @param origin 原始数据列表
     * @return 处理后数据列表
     */
    public List<RawDataDO> ridNullPointAndAverage(List<RawDataDO> origin) {
        return ridAverage(ridNullPoint(origin));
    }
}
