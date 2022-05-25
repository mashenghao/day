package bReflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 反射获取类的信息
 *
 * @author mahao
 * @date 2019年5月2日 上午11:08:09
 */
public class Demo3 {

	public static void main(String[] args) {
		Demo3.printClass(new String());
	}

	/**
	 * 打印类的信息，包括函数和方法，返回值
	 * 
	 * @param obj
	 */
	public static void printClass(Object obj) {
		// 1.获得类的类类型实例
		Class clazz = obj.getClass();

		// 2.获得所有的方法列表
		/*
		 * getMethods()是获取所有public的方法，包括父类 getDeclaredMethod()是获取本类声明的方法，不问权限
		 */
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			// 获取返回类型
			Class returnType = method.getReturnType();
			System.out.print(returnType.getSimpleName() + " ");

			// 获取方法名
			String methodName = method.getName();
			System.out.print(methodName);

			// 获取参数列表
			System.out.print(" ( ");
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (Class<?> pt : parameterTypes) {
				System.out.print(pt.getSimpleName() + " ");
			}
			System.out.println(")");

			Parameter[] parameters = method.getParameters();
			for (Parameter parameter : parameters) {
				System.out.println(parameter);
			}
			System.out.println("-*---------------------------\n");
		}
	}
}
