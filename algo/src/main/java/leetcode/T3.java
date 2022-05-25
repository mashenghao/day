package leetcode;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author: mahao
 * @date: 2019/9/22
 */
public class T3 {


    /**
     * 暴力破解，一个字符一个字符的找n*n
     *
     * @param s a b cabcabcb
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int min = 0;
        int sum = 0;

        for (int i = 1; i < s.length(); i++) {
            int j = min;
            for (; j < i; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    min = j + 1;
                    break;
                }
            }
            if (sum < i - min) {
                sum = i - min;
            }
        }

        // System.out.println(s.substring(min, max));
        return sum + 1;
    }

    public static void main(String[] args) {
        System.out.println(new T3().lengthOfLongestSubstring("bbbb"));

    }
}
