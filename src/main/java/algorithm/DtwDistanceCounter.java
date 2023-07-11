package algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 刀氏春秋
 * @date 2023/7/11
 * 算法介绍：
 * DTW距离中的T明确表示其处理的是时序数据，对于相位识别来说，先前用过的多元线性回归算法并没有考虑到时间信息。
 * 因此新的算法考虑到数据的时序特征，采用DTW距离分析曲线相似性
 */
public class DtwDistanceCounter {
    /**
     * 求解三个浮点数最小值，并返回该值
     *
     * @param a 第一个参数
     * @param b 第二个参数
     * @param c 第三个参数
     * @return 最小值数值
     */
    private Double minThreeDoubleValue(Double a, Double b, Double c) {
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
    private Integer minThreeDoublePos(Double a, Double b, Double c) {
        if (a < b && a < c) return 1;
        else if (b < a && b < c) return 2;
        else return 3;
    }

    /**
     * 输出序列比较矩阵，用于调试
     *
     * @param seqMatrix 序列比较矩阵
     */
    private void printSeqMatrix(Double[][] seqMatrix) {
        StringBuilder result = new StringBuilder();
        for (Double[] matrix : seqMatrix) {
            for (int j = 0; j < seqMatrix[0].length; j++) {
                result.append(String.format("%-9f", matrix[j]));
            }
            result.append("\n");
        }
        System.out.println(result);
    }

    /**
     * 组装时间序列比较矩阵（DTW距离算法专用）
     *
     * @return 序列比较矩阵
     */
    private Double[][] assembleSeqMatrix(List<Double> subject, List<Double> object) {
        // 初始化的话一般全都是0
        Double[][] seqMatrix = new Double[object.size()][subject.size()];

        // 首先全部处理为0
        for (int i = 0; i < object.size(); i++) {
            for (int j = 0; j < subject.size(); j++) {
                // 欧几里得基础值
                seqMatrix[i][j] = Math.abs(subject.get(j) - object.get(i));
                if (i == 0 && j == 0) {
                    // 如果i和j都为0，此时无需计算
                    continue;
                }
                if (i != 0 && j == 0) {
                    // 如果i不为0，j为0，表示位于左侧列，直接加上面即可
                    seqMatrix[i][j] += seqMatrix[i - 1][j];
                } else if (i == 0) {
                    // 如果i为0，j不为0，表示位于上侧行，直接加左即可
                    seqMatrix[i][j] += seqMatrix[i][j - 1];
                } else {
                    // 其他一般情况，不触及边界，要找出左，左上，上三个值中的最小值
                    seqMatrix[i][j] += minThreeDoubleValue(seqMatrix[i - 1][j], seqMatrix[i - 1][j - 1], seqMatrix[i][j - 1]);
                }
            }
        }

        return seqMatrix;
    }

    /**
     * 寻找序列比较矩阵中的最短路径
     *
     * @param seqMatrix 序列比较矩阵
     * @return 最短路径列表
     */
    private List<Double> getShortestPath(Double[][] seqMatrix) {
        int i = seqMatrix.length - 1;
        int j = seqMatrix[0].length - 1;
        // 当二者同时为0时，跳出算路循环
        List<Double> path = new LinkedList<>();
        path.add(seqMatrix[i][j]);
        while (i != 0 || j != 0) {
            // 首先考虑几种边界情况
            if (i == 0) {
                // 第一种情况，已经处于最上行，此时只能左移
                path.add(seqMatrix[i][--j]);
            } else if (j == 0) {
                // 第二种情况，已经处于最左行，此时只能上移
                path.add(seqMatrix[--i][j]);
            } else {
                // 第三种情况，i和j都不为0，属于常规情况，此时寻找最小的方向移动
                switch (minThreeDoublePos(seqMatrix[i - 1][j], seqMatrix[i - 1][j - 1], seqMatrix[i][j - 1])) {
                    case 1:
                        // 左值最小，向左移动
                        path.add(seqMatrix[--i][j]);
                        break;
                    case 2:
                        // 左上值最小，向左上移动
                        path.add(seqMatrix[--i][--j]);
                        break;
                    case 3:
                        // 上值最小，向上移动
                        path.add(seqMatrix[i][--j]);
                        break;
                }
            }
        }

        return path;
    }

    /**
     * 计算DTW距离
     *
     * @param subject 被比较序列
     * @param object  比较序列
     * @return DTW距离
     */
    public Double calculateDtwDistance(List<Double> subject, List<Double> object) {
        Double dtwDistance = 0d;
        // 首先生成DTW距离矩阵
        Double[][] seqMatrix = this.assembleSeqMatrix(subject, object);
        printSeqMatrix(seqMatrix);

        // 随后从右下角出发，根据贪心算法寻找最短路径
        List<Double> path = getShortestPath(seqMatrix);
        System.out.println(path);

        return dtwDistance;
    }
}
