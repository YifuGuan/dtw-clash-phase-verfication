package stage;

import algorithm.DtwDistanceCounter;
import stage.pojo.domain.RawDataDO;
import stage.pojo.domain.ResultDO;
import utils.CopyUtil;
import utils.MathUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/18
 * 第三阶段：根据DTW算法开展电表聚类
 * 输入：RawData数据列表
 * 输出：ResultDTO结果列表
 */
public class DistanceCalculator {
    /**
     * 计算两个电表量测数据曲线的DTW距离
     *
     * @param meter1 电表1数据对象
     * @param meter2 电表2数据对象
     * @return DTW距离
     */
    private Double distanceBetweenTwoMeter(RawDataDO meter1, RawDataDO meter2) {
        DtwDistanceCounter counter = new DtwDistanceCounter();
        return counter.calculateDtwDistance(meter1.getPoints(), meter2.getPoints());
    }

    /**
     * 判断表相位
     *
     * @param centralA       A相聚类中心
     * @param centralB       B相聚类中心
     * @param centralC       C相聚类中心
     * @param toBeClassified 待识别电表
     * @return 聚类结果
     */
    private String judgeClosesCentral(RawDataDO centralA, RawDataDO centralB, RawDataDO centralC, RawDataDO toBeClassified, ResultDO result) {
        // 计算处待分类表数据同聚类中心的距离，并根据远近判断所在相位
        result.setDtwDistance(
                distanceBetweenTwoMeter(centralA, toBeClassified),
                distanceBetweenTwoMeter(centralB, toBeClassified),
                distanceBetweenTwoMeter(centralC, toBeClassified)
        );
        switch (MathUtil.minThreeDoublePos(result.getDtwA(), result.getDtwB(), result.getDtwC())) {
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
        }
        // 若无法计算或无法判断，识别为空
        return "null";
    }

    /**
     * 聚类中心不变的聚类相位识别
     *
     * @param data 数据列表
     * @return 结果列表
     */
    public List<ResultDO> verifyPhaseWithoutCentralReload(List<RawDataDO> data) {
        List<ResultDO> results = new LinkedList<>();

        // 不重载聚类中心，中心由三个总表构成，是固定的，按照数据规范，前三个行数据保存的是总表三相数据
        // 按位置顺序分别对应A、B和C相
        RawDataDO centralA = data.get(0);
        RawDataDO centralB = data.get(1);
        RawDataDO centralC = data.get(2);

        // 分析总表外其他表的相位
        for (RawDataDO tuple : data.subList(3, data.size())) {
            ResultDO result = new ResultDO();
            result.setPhase(judgeClosesCentral(centralA, centralB, centralC, tuple, result));
            CopyUtil.copyHeadersToResult(tuple, result);
            results.add(result);
        }
        return results;
    }
}
