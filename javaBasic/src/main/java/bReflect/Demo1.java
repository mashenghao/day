package bReflect;

public class Demo1 {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		// Foo的实例对象如何表示
		Foo foo = new Foo();

		// Foo这个类 也是一个实例对象，Class类的实例对象如何表示
		// 任何一个类都是Class的实例对象，这个实例对象的获取有三种方式

		// 第一种方式: 这句话说明了，class是类的静态成员变量
		Class c1 = Foo.class;

		// 第二种方式:对象通过这个方法
		Class c2 = foo.getClass();

		// Class在官网介绍是 （class Type），表示是类的类型，吧所有类看做具体的实例对象
		// 对象中方法不同。属性不同，但都是同一个类类型的实例；

		// 第三种方式
		Class c3 = Class.forName("bReflect.Foo");

		//这时的c1 c2 C3 和Foo这个类表示的是同一个对象
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		
		//通过类的类类型创建该类的对象
		Foo instance = (Foo) c1.newInstance();
		System.out.println(instance);
		
		
	}
}

class Foo {

}