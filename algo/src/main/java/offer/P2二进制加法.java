package offer;

/**
 * @author mahao
 * @date 2022/08/12
 */
public class P2二进制加法 {
    public static void main(String[] args) {
        String s = addBinary("1010", "1011");
        System.out.println(s);
    }

    /**
     * @param a "10"
     * @param b "11"
     * @return "101"
     * <p>
     * 11 +11 = 110
     */
    public static String addBinary(String a, String b) {
        char[] c1 = a.length() >= b.length() ? a.toCharArray() : b.toCharArray();
        char[] c2 = a.length() >= b.length() ? b.toCharArray() : a.toCharArray();
        int t = 0;
        for (int i = c1.length - 1; i >= 0; i--) {
            t += c1[i] == '1' ? 1 : 0;
            if (c1.length - 1 - i < c2.length) {
                t += c2[c2.length - c1.length + i] == '1' ? 1 : 0;
            }
            c1[i] = t % 2 == 1 ? '1' : '0';
            t = t > 1 ? 1 : 0;
        }
        if (t == 1) {
            return "1" + new String(c1);
        }
        return new String(c1);
    }
}
