package bReflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ClassUtil {
	/**
	 * 打印类的信息，包括函数和方法，返回值
	 * 
	 * @param obj
	 */
	public static void printClassMethod(Object obj) {
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

	/**
	 * 打印属性信息
	 * 
	 * @param obj
	 */
	public static void printClassField(Object obj) {
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 获取属性的类型，所以返回值是类类型 
			Class fieldClazz = field.getType();
			System.out.print(fieldClazz.getSimpleName() + " ");
			String name = field.getName();
			System.out.println(name);
		}
	}

	/**
	 * 打印构造函数信息
	 * 
	 * @param clazz
	 */
	public static void printConstractor(Class clazz) {
		Constructor[] constructors = clazz.getDeclaredConstructors();
		for (Constructor constructor : constructors) {
			String name = constructor.getName();
			System.out.print(name + "( ");
			Parameter[] parameters = constructor.getParameters();
			for (Parameter parameter : parameters) {
				String simpleName = parameter.getType().getSimpleName();
				System.out.print(simpleName + " " + parameter.getName() + " ");
			}
			System.out.println(")");
		}
	}
}
