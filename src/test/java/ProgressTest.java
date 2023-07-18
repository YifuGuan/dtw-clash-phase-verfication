import org.junit.jupiter.api.Test;
import stage.DataLoader;
import stage.DataPreTreater;
import stage.DistanceCalculator;
import stage.pojo.domain.RawDataDO;
import stage.pojo.domain.ResultDO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 */
public class ProgressTest {
    @Test
    void testDataImport() {
        DataLoader loader = new DataLoader();

        List<RawDataDO> loadedRawData = loader.importData("9992");
        assertEquals(50, loadedRawData.size());
    }

    @Test
    void testDataPreTreat() {
        DataLoader loader = new DataLoader();
        List<RawDataDO> loadedRawData = loader.importData("9992");

        DataPreTreater preTreater = new DataPreTreater();
        List<RawDataDO> toBeAnalysis = preTreater.ridNullPointAndAverage(loadedRawData);

        System.out.println(toBeAnalysis);
    }

    @Test
    void testDistanceCalculate() {
        DataLoader loader = new DataLoader();
        List<RawDataDO> loadedRawData = loader.importData("9992");

        DataPreTreater preTreater = new DataPreTreater();
        List<RawDataDO> toBeAnalysis = preTreater.ridNullPointAndAverage(loadedRawData);

        DistanceCalculator calculator = new DistanceCalculator();
        List<ResultDO> results = calculator.verifyPhaseWithoutCentralReload(toBeAnalysis);

        System.out.println(results);
    }
}
