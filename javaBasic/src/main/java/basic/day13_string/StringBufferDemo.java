package basic.day13_string;

/**
 * 
 *StringBuffer 常用API的介绍
 */
public class StringBufferDemo {
	
	public static void main(String[] args) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("aaa").append(false);//方法链调用
		System.out.println(sBuffer.toString());
	}
	
	
}
