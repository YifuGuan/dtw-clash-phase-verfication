package models.pojo.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/8/14
 */
@Data
public class FeaturesDO {
    private HashMap<Integer, List<PeekDO>> peeks;

    public FeaturesDO() {
        this.peeks = new HashMap<>();
    }

    public void appendDayPeeks(List<PeekDO> dayPeeks, Integer dayIndex) {
        peeks.put(dayIndex, dayPeeks);
    }
}
