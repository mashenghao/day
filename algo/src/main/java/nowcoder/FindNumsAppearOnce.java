package nowcoder;

/**
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。
 * 请写程序找出这两个只出现一次的数字。
 */
public class FindNumsAppearOnce {


    //num1,num2分别为长度为1的数组。传出参数
    //将num1[0],num2[0]设置为返回结果
    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {

        int n = array[0];
        for (int i = 1; i < array.length; i++) {
            n = n ^ array[i];
        }
    }

    public static void main(String[] args) {

    }
}
