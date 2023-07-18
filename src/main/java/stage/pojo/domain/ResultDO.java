package stage.pojo.domain;

import lombok.Data;

/**
 * @author 刀氏春秋
 * @date 2023/7/18
 */
@Data
public class ResultDO {
    String tgId;
    String assetNo;
    String phase;
    Double dtwA;
    Double dtwB;
    Double dtwC;

    /**
     * 设置DTW距离
     *
     * @param dtwA 与A相聚类中心距离
     * @param dtwB 与B相聚类中心距离
     * @param dtwC 与C相聚类中心距离
     */
    public void setDtwDistance(Double dtwA, Double dtwB, Double dtwC) {
        this.dtwA = dtwA;
        this.dtwB = dtwB;
        this.dtwC = dtwC;
    }
}
