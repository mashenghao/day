package nowcoder.p3;

/**
 * 替换空格：
 * <p>
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 */
public class replaceSpace {

    public String replaceSpace(StringBuffer str) {
        int size = str.length();
        StringBuffer sb = new StringBuffer(str.length());
        for (int i = 0; i < size; i++) {
            if (' ' == str.charAt(i)) {
                sb.append("%20");
            }else{
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }
}

