package models.phaseVerfication;

import com.alibaba.excel.EasyExcel;
import models.pojo.domain.ResultDO;
import models.pojo.dto.ResultDTO;
import utils.CopyUtil;

import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/18
 */
public class ResultExporter {
    public void exportResultsListAsExcel(List<ResultDO> origin) {
        List<ResultDTO> results = CopyUtil.copyAllToResultDTOList(origin);
        EasyExcel.write("src/main/resources/result/res-" + results.get(0).getTgId() + ".xlsx", ResultDTO.class)
                .sheet("相位识别结果").doWrite(() -> results);
    }
}
