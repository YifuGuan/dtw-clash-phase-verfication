package models.pojo.domain;

import lombok.Data;

/**
 * @author 刀氏春秋
 * @date 2023/8/24
 */
@Data
public class PeekDO {
    private Integer startIndex;
    private Integer peekWidth;
    private Double kurtosis;

    public PeekDO(Integer startIndex, Integer peekWidth) {
        this.startIndex = startIndex;
        this.peekWidth = peekWidth;
    }
}
