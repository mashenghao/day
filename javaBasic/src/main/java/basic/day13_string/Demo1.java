package basic.day13_string;

import org.junit.Test;

/**
 * 
 * String的测试练习
 *
 */
public class Demo1 {

	@Test
	public void test1() {
		String s1 = "abc";//"abc"是一个对象，将对象赋予类变量s1
		String s2 = new String("abc");//这里是两个对象，在内存中存在两个，包括对象abc 和 new 出来的对象
		String s3 = "abc";  //因为String类型数据是不可变的，所以‘abc’被放在了常量池中，这里的‘abc’ַ和s1的‘abc’是
							//同一个常量abc对象，因此二者的内存地址是一样的。
		
		System.out.println(s1==s2);//false
		System.out.println(s1==s3);//true 这是这号i
		
		//+ 源码大意为: (new StringBuffer()).append(s3).append("bbb").toString;
		//是新new出一个新的StringBuffer对象，
		s3 = s3+"bbb";//这时候s3已经不指向"abc"，源对象依旧存在，s3是新的string类型的对象
		String s4 = "abcbbb";
		String s5 = new String("abcbbb");
		System.out.println(s3);
		System.out.println(s3==s4);//false  s3是一个新的String对象
		System.out.println(s4=="abcbbb");//true  这个“abcbbb”属于同一个常量池中的对象
		System.out.println(s3==s5);
	}
	
	/**
	 * String 
	 */
	@Test
	public void test2() {
		String str = "abda";
		str.indexOf("aa");
		str.contains("aa");
		str.replace("a","DSB");
 	}
	
}
