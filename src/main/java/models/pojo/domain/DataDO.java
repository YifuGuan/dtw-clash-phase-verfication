package models.pojo.domain;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/17
 */
@Data
public abstract class DataDO {
    private String assetNo;
    private String tgId;
    private List<List<Double>> points;

    public DataDO() {
        this.points = new LinkedList<>();
    }

    /**
     * 将单一数据点加入到数据矩阵末梢，必要时另起一行
     *
     * @param data 数据点
     */
    public void appendSinglePoint(Double data) {
        // 情况1：此时矩阵为空，没有任何数据
        // 情况2：如果已经有数据存储在内了，检查是否到达换行节点
        if (points.size() == 0 || points.get(points.size() - 1).size() == 96) {
            // 最后一行已有96点，满了，故换行到下一行
            List<Double> anotherDay = new LinkedList<>();
            anotherDay.add(data);
            points.add(anotherDay);
        } else {
            // 最后一行还不够96个点，不换行直接加
            points.get(points.size() - 1).add(data);
        }
    }

    /**
     * 向数据点列表中添加单日数据列表
     *
     * @param data 数据点列表
     */
    public void appendDayPoints(List<Double> data) {
        this.getPoints().add(data);
    }

    /**
     * 将按日整理的二维列表转化为单维列表
     *
     * @return 单维数据列表
     */
    public List<Double> getAllPointsInSingleList() {
        List<Double> multipleDaysPoints = new LinkedList<>();
        for (List<Double> dayPoints : this.points) {
            multipleDaysPoints.addAll(dayPoints);
        }
        return multipleDaysPoints;
    }
}
