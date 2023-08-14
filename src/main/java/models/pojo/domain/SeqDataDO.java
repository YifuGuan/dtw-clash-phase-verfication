package models.pojo.domain;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author 刀氏春秋
 * @date 2023/8/11
 * 电流量测数据（带明确时序编号）
 */
public class SeqDataDO extends DataDO {
    private TreeMap<Integer, Double> seqPoints;

    public SeqDataDO() {
        this.seqPoints = new TreeMap<>();
    }

    public void setSeqPoints(TreeMap<Integer, Double> points) {
        this.seqPoints = points;
    }

    public TreeMap<Integer, Double> getSeqPointsWithIndex() {
        return this.seqPoints;
    }

    public List<Double> getSeqPointsWithoutIndex() {
        List<Double> res = new LinkedList<>();
        for (Integer index : this.seqPoints.keySet()) {
            res.add(this.seqPoints.get(index));
        }
        return res;
    }
}
