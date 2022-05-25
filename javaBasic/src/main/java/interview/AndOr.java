package interview;

/**
 * 逻辑表达式之间的优先级
 *
 * @author: mahao
 * @date: 2019/9/7
 */
public class AndOr {

    public static void main(String[] as) {
        boolean b1 = true, b2 = true, b3 = false;

        if (b3 && b1 || b2) {
            System.out.println("true && false or true");
        }
    }
}
