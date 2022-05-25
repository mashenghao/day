package interview;

/**
 * 9-9乘法表
 *
 * @author: mahao
 * @date: 2019/10/21
 */
public class NNResult {


    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.printf("%d * %d = %d , ", j, i, i * j);
            }
            System.out.println();
        }
    }

}
