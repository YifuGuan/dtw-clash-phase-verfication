import models.phaseVerfication.RawDataLoader;
import org.junit.jupiter.api.Test;
import models.publicModels.DataLoader;
import models.phaseVerfication.DataPreTreater;
import models.phaseVerfication.DistanceCalculator;
import models.phaseVerfication.ResultExporter;
import models.pojo.domain.RawDataDO;
import models.pojo.domain.ResultDO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 */
public class ProgressTest {
    RawDataLoader loader = new RawDataLoader();
    DataPreTreater preTreater = new DataPreTreater();
    DistanceCalculator calculator = new DistanceCalculator();
    ResultExporter exporter = new ResultExporter();


    @Test
    void testBasicOperation() {
        System.out.println((double) (24 / (double) 5));
    }

    @Test
    void testDataImport() {
        List<RawDataDO> loadedRawData = loader.importData("9992");
        assertEquals(50, loadedRawData.size());
    }

    @Test
    void testDataPreTreat() {
        List<RawDataDO> loadedRawData = loader.importData("9992");
        List<RawDataDO> toBeAnalysis = preTreater.ridNullPointAndAverage(loadedRawData);

        System.out.println(toBeAnalysis);
    }

    @Test
    void testDistanceCalculate() {
        List<RawDataDO> loadedRawData = loader.importData("9992");
        List<RawDataDO> toBeAnalysis = preTreater.ridNullPointAndAverage(loadedRawData);
        List<ResultDO> results = calculator.verifyPhaseWithoutCentralReload(toBeAnalysis);

        System.out.println(results);
    }

    @Test
    void testResultExport() {
        List<RawDataDO> loadedRawData = loader.importData("9992");
        List<RawDataDO> toBeAnalysis = preTreater.ridNullPointAndAverage(loadedRawData);
        List<ResultDO> results = calculator.verifyPhaseWithoutCentralReload(toBeAnalysis);

        exporter.exportResultsListAsExcel(results);
    }
}
