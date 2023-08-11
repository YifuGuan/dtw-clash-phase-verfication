package models.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 刀氏春秋
 * @date 2023/7/18
 */
@Data
public class ResultDTO {
    @ExcelProperty("台区编号")
    String tgId;
    @ExcelProperty("电表资产号")
    String assetNo;
    @ExcelProperty("相位")
    String phase;
    @ExcelProperty("A相距离")
    Double dtwA;
    @ExcelProperty("B相距离")
    Double dtwB;
    @ExcelProperty("C相距离")
    Double dtwC;
}
