import enums.math.PlotBoxEnum;
import models.pojo.domain.SeqDataDO;
import models.tgVerfication.SeqDataLoader;
import org.junit.jupiter.api.Test;
import utils.MathUtil;

import java.util.HashMap;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/8/14
 */
public class TgVerifyTest {
    SeqDataLoader loader = new SeqDataLoader();

    @Test
    void testSeqDataLoad() {
        List<SeqDataDO> seqData = loader.importData("9992");

        HashMap<PlotBoxEnum, Double> plotModel = MathUtil.generatePlotBox(seqData.get(3).getSeqPointsWithoutIndex());
    }
}
