import enums.math.PlotBoxEnum;
import models.pojo.domain.RawDataDO;
import models.tgVerfication.SeqDataLoader;
import models.tgVerfication.TgFeatureGenerator;
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
    TgFeatureGenerator generator = new TgFeatureGenerator();

    @Test
    void testSeqDataLoad() {
        List<RawDataDO> rawData = loader.importData("4134");

        generator.generateFeature(rawData.get(0));
    }
}
