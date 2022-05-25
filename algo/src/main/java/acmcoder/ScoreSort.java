package acmcoder;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author: mahao
 * @date: 2020/3/16
 */
public class ScoreSort {

    public static void main(String[] args) {
        int[][] scores = {{101, 1, 1, 1}, {102, 2, 2, 2}, {103, 2, 2, 1}, {104, 3, 3, 3}, {105, 4, 4, 4}};
        int[][] sort = sort(scores);
        for (int[] ints : sort) {
            for (int i : ints) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static int[][] sort(int[][] scores) {

        //使用TreeSet集合，treeSet存入的元素会自动排序，排序规则是Comparator接口定义的
        TreeSet<int[]> set = new TreeSet<>((o1, o2) -> {//实现这个接口，定义排序规则
            //o2 和 o1 是集合中的两个元素，o2[4]存储的是总成绩，用总成绩去比较。
            int c = o2[4] - o1[4];
            return c == 0 ? 1 : c;//对于总成绩相同的数据，顺序排序
        });

        for (int i = 0; i < scores.length; i++) {
            int[] ints = new int[5];
            int sum = 0;
            ints[0] = scores[i][0];
            for (int j = 1; j < scores[i].length; j++) {
                ints[j] = scores[i][j];
                sum += ints[j]; //计算每一个同学的总成绩
            }
            ints[4] = sum;

            set.add(ints);//存入到有序集合中并比较。
        }

        //将集合中的元素放到二维数组中。
        int[][] result = new int[scores.length][];
        Iterator<int[]> it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            int[] ints = it.next();
            result[i++] = ints;
        }
        return result;
    }

    
}
