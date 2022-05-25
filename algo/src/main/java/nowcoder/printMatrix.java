package nowcoder;

import java.util.ArrayList;

/**
 * 顺时针打印矩阵:
 * <p>
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
 * 例如，如果输入如下4 X 4矩阵：
 * 1  2  3  4
 * 5  6  7  8
 * 9  10 11 12
 * 13 14 15 16
 * 则依次打印出数字
 * 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 */
public class printMatrix {

    public ArrayList<Integer> printMatrix(int[][] matrix) {

        int m = matrix.length;//行  3
        int n = matrix[0].length;//列  4列

        ArrayList<Integer> list = new ArrayList<>();
        int ceil = ((m < n ? m : n) + 1) / 2;//一共循环几圈

        for (int k = 0; k < ceil; k++) {
            //打印从左向右的数据
            for (int j = k; j <= n - 1 - k; j++) {
                System.out.println(matrix[k][j] + " -");
                list.add(matrix[k][j]);
            }
            for (int i = k + 1; i <= (m - 1) - k; i++) {
                System.out.println(matrix[i][(n - 1) - k] + " |");
                list.add(matrix[i][(n - 1) - k]);
            }
            for (int j = (n - 1) - k - 1; j >= k && m - k - 1 != k; j--) {
                System.out.println(matrix[m - k - 1][j] + " *");//只有一行，会导致重复
                list.add(matrix[m - k - 1][j]);
            }
            for (int i = (m - 1 - k - 1); i > k && n - k - 1 != k; i--) {//只有一列，会导致重复
                System.out.println(matrix[i][k] + " /");
                list.add(matrix[i][k]);
            }
        }
        return list;
    }


    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {1}, {2}, {3}, {4}
                //{1, 2, 3, 4},
                //  {5, 6, 7, 8},
                //  {9, 10, 11, 12},
                // {13, 14, 15, 16}
        };

        printMatrix p = new printMatrix();
        p.printMatrix(arr);
    }
}
