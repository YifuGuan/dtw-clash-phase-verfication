package analysis;

import algorithm.DtwDistanceCounter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 * 这个类负责DTW距离算法的基础测试
 */
public class BasicTest {
    public static void main(String[] args) {
        DtwDistanceCounter counter = new DtwDistanceCounter();
        List<Double> subject = new LinkedList<>();
        subject.add(3.0);
        subject.add(4.0);
        subject.add(5.0);
        List<Double> object = new LinkedList<>();
        object.add(1.0);
        object.add(4.0);
        object.add(2.0);
        object.add(6.0);
        counter.calculateDtwDistance(object, subject);
    }
}
