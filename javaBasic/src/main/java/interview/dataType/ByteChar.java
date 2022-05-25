package interview.dataType;

import org.junit.Test;

/**
 * @author: mahao
 * @date: 2019/9/6
 */
public class ByteChar {

    /**
     * 在java中，一个字符数据类型占了两个字节，所以他可以存储汉字。
     * 用两个字节，来表示，这个char不涉及到字符编码集上的问题。那个是字符串的问题。
     */
    @Test
    public void demo1() {
        char ch1 = 'a';
        char ch2 = '你';
        /*
        无法直接获取到char类型的长度，通过向下转型，和向上转型比较；
        byte 是一个字节，char转成byte需要转型；
        short 是两个字节，可以直接转换
        int 是四个字节，int转成char，需要向下转型

        但是，java中操作char，是当做int类型操作了。
         */
        byte b1 = (byte) ch1;
        short s1 = (short) ch1;
        int i1 = ch2;


    }

    /**
     * 。在String中，一个英文字符占1个字节，而中文字符根据编码的不同所占字节数也不同。
     * 在UTF-8编码下，一个中文字符占3个字节；而使用GBK编码时一个中文字符占2个字节。测试代码如下
     */
    @Test
    public void demo2() {

        char[] chs = {'a', 'b', '你'};
        String a = new String(chs);
        System.out.println(a.getBytes().length);
        System.out.println(a.getBytes().length);
    }


}
