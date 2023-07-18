package utils;

/**
 * @author 刀氏春秋
 * @date 2023/7/18
 */
public class MathUtil {
    /**
     * 求解三个浮点数最小值，并返回该值
     *
     * @param a 第一个参数
     * @param b 第二个参数
     * @param c 第三个参数
     * @return 最小值数值
     */
    public static Double minThreeDoubleValue(Double a, Double b, Double c) {
        if (a < b && a < c) return a;
        else if (b < a && b < c) return b;
        else return c;
    }

    /**
     * 求解三个浮点数最小值，返回该值所在位置
     *
     * @param a 第一个参数
     * @param b 第二个参数
     * @param c 第三个参数
     * @return 第几个参数是最小的（1起始编号）
     */
    public static Integer minThreeDoublePos(Double a, Double b, Double c) {
        if (a < b && a < c) return 1;
        else if (b < a && b < c) return 2;
        else return 3;
    }
}
