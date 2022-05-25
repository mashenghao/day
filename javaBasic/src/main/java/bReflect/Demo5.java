package bReflect;

import java.lang.reflect.Method;

/**
 * 方法的反射操作：框架的自动化原理，通过指定方法，通过反射
 * 读取这个类型，进行方法执行；
 *
 * @author mahao
 * @date 2019年4月17日 下午9:32:23
 */
public class Demo5 {

	// 通过方法反射获取方法并且执行
	public static void main(String[] args) throws Exception {
		Class clazz = Hanlder.class;
		// 获取指定的方法
		Method method = clazz.getMethod("hand", new Class[] { String.class, String.class });
		// 执行这个方法，传入实例对象和方法参数；
		Object object = method.invoke(new Hanlder(), "aa", "bb");
		// 打印返回结果
		System.out.println(object);
	}
}

class Hanlder {

	public String hand(String s1, String s2) {
		return s1 + s2;
	}

	public String hand(String s1) {
		return s1;
	}
}