package models.pojo.domain;

import lombok.Data;

import java.util.TreeMap;

/**
 * @author 刀氏春秋
 * @date 2023/8/11
 * 电流量测数据（带明确时序编号）
 */
public class SeqDataDO extends DataDO {
    private TreeMap<Integer, Double> points;

    public SeqDataDO() {
        this.points = new TreeMap<>();
    }

    public void setSeqPoints(TreeMap<Integer, Double> points) {
        this.points = points;
    }

    public TreeMap<Integer, Double> getSeqPoints() {
        return this.points;
    }
}
