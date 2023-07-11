package stage.pojo.domain;

import lombok.Data;

import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 * 算法直接使用的DO数据
 */
@Data
public class RawDataDO {
    private String assetNo;
    private String tgId;
    private List<Double> points;
}