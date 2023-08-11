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
    private List<Double> points;

    public DataDO() {
        this.points = new LinkedList<>();
    }

    /**
     * 向数据点列表中添加点
     *
     * @param data 数据点
     */
    public void appendPoint(Double data) {
        this.getPoints().add(data);
    }
}
