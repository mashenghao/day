package basic.day25;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用mainClass模仿鼠标右键Run Junit<br>
 * 过程就是： 通过反射，获取带有myJunit的注解的方法，进而执行
 * 
 * @author mahao
 * @date 2019年4月24日 下午4:48:11
 */
public class MainClass {

	public static void main(String[] args) {
		// 1.获取Demo的类类型,三种
		try {
			Class clazz = Class.forName(args[0]);
			Object object = clazz.newInstance();
			// 2.获取该类的所有方法
			Method[] methods = clazz.getMethods();
			for (Method m : methods) {
				//3.判断是否有myJunit的自定义注解
				boolean flag = m.isAnnotationPresent(MyJunit.class);
				if(flag) {//存在该注解，运行方法
					MyJunit junit = m.getAnnotation(MyJunit.class);
					String param = junit.param();
					m.invoke(object, param);
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
