package nowcoder.p3;

/**
 * 空格替换最优解：%20
 * 思路:
 * 从后往前替换查找，首先查出来有多少个空格 count，将数组扩充到 length + 2*count ，
 * 如果是正常的字符，下标为 i ，字符在新的位置应该为j = i + 2*count ，
 * 如果是空格字符，小标为 i ,那么存放的的位置是 j=i+2*count ， j=0; j-1=2; j-2=%; 同时count--，表示空格踢出去一个，往后顺延的
 * 个数也会减少2个，（虽然替换的字符是%20，有3个字符，但是原本的空格占据一个字符）。
 */
public class ReplaceSpace_true {

    public String replaceSpace(StringBuffer str) {

        if (null == str)
            return null;
        int n = str.length(), count = 0;//count表计空格个数
        for (int i = 0; i < n; i++)
            if (' ' == str.charAt(i))
                count++;
        int index;
        str.setLength(n + 2 * count);
        for (int i = n - 1; count > 0 && i >= 0; i--) {//从后往前循环这个字符串，结束条件是，没有空格了，或者遍历结束了
            index = i + 2 * count;//字符应该插入的新位置

            if (' ' == str.charAt(i)) {
                //是空格，则进行替换%20，往前三个位置都要替换
                str.setCharAt(index--, '0');
                str.setCharAt(index--, '2');
                str.setCharAt(index, '%');

                count--;//一定不要忘记，这个空格字符个数减少了，count减少后，字符顺延个数也会减少
            } else {
                //不是空格，则将原本字符的位置，往后顺延i+2*count个数，给空格字符腾位置
                str.setCharAt(index, str.charAt(i));
            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        System.out.println(new ReplaceSpace_true().replaceSpace(new StringBuffer("12  3 ")));
    }
}
