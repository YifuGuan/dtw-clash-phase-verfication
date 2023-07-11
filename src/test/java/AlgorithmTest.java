import algorithm.DtwDistanceCounter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 */
public class AlgorithmTest {
    @Test
    void testDtwDistance() {
        // 生成被比较序列
        List<Double> subject = new LinkedList<>();
        subject.add(3.0);
        subject.add(4.0);
        subject.add(5.0);

        // 生成比较序列
        List<Double> object = new LinkedList<>();
        object.add(1.0);
        object.add(4.0);
        object.add(2.0);
        object.add(6.0);

        // 导入比较序列并生成两曲线DTW距离
        DtwDistanceCounter counter = new DtwDistanceCounter();
        Double dtwDistance = counter.calculateDtwDistance(object, subject);
        assertTrue((13d - dtwDistance) < 1e-4);
    }
}
