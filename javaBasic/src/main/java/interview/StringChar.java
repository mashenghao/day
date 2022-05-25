package interview;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mahao
 * @date: 2019/10/18
 */
public class StringChar {

    public static void main(String[] args) {
        String s = "hello";
        char[] c = {'h', 'e', 'l', 'l', 'o'};
        System.out.println(s.equals(c));
        s.split("111");
        String str2 = new String("12300450060");
        List<String> list = mySpilt(str2, "0");
        System.out.println(list);


    }


    //12300456   //00
    private static List<String> mySpilt(String s, String r) {
        List<String> list = new ArrayList<>();
        char[] src = s.toCharArray();
        char[] reg = r.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < src.length; ) {
            int j = 0;
            for (j = 0; j < reg.length; ) {
                if (i < src.length && src[i] == reg[j]) {
                    i++;
                    j++;
                } else {
                    for (int k = 0; k < j - 1; k++) {
                        sb.append(reg[k]);
                    }
                    if (j > 0)
                        i--;
                    break;
                }
            }
            if (j == reg.length || i == src.length) {
                list.add(sb.toString());
                sb = new StringBuffer();
            } else {
                sb.append(src[i]);
                i++;
            }
        }
        if (sb.toString().length() > 0)
            list.add(sb.toString());

        return list;
    }
}
