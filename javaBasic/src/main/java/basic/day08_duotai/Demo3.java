package basic.day08_duotai;

/**
 * 父类和子类含有同名成员变量赋值问题
 *
 * @author mahao
 * @date: 2019年2月27日 下午7:19:27
 */

public class Demo3 extends Fu {

	int i = 100;
	
	//测试二时，关闭方法
//	@Override
//	void set() {
//		i = 1000;
//	}
	
	public static void main(String[] args) {

		Fu f = new Fu();
		Demo3 z = new Demo3();
		Fu zi = new Demo3();
		// 测试一：
		System.out.println("引用型和实例都是fu类型，结果为10--" + f.i);//10
		System.out.println("结果为子类型数据" + z.i);//100
		System.out.println("父类引用型变量持有子类对象时，属性值看应用型变量" + zi.i);//10

/*		// 测试二： 父类有的方法没有，子类数据改变
		z.set();//子类对象调用父类中的方法，改变成员变量的值
		Fu z2 = z;
		System.out.println("子类对象数据没有发生改变"+ z.i);//100
		System.out.println( "父类对象数据发生改变"+z2.i);//20
		//----
		zi.set();
		System.out.println(zi.i);//20   持有的是父类成员属性，所以数据会发生改变
*/
		//测试三： 子类覆盖父类方法，成员变量属性变化
		z.set();
		Fu z2 = z;
		System.out.println("子类对象数据发生改变"+ z.i);//1000
		System.out.println( "父类对象数据没有发生改变"+z2.i);//10
		
		//测试四：调用的是子类的方法，改变了子类数据，显示的是父类的变量
		zi.set();
		Demo3 d = (Demo3) zi;
		System.out.println(zi.i);//10
		System.out.println(d.i);//1000
	/**
	 * 总结：
	 * 	1. 子类对象在初始化时，子类对象和父类对象在两个不同内存区域，子类和父类含有相同的成员变量，
	 * 	成员变量的数值是跟从引用型变量所属的类。当调用方法，改变成员变量数值时，改变的数据是方
	 * 	法所属的类。
	 * 
	 *	2. 当采用多态时，父类持有子类对象 Fu zi = new Demo3(),父类和子类含有相同方法和变量，
	 * 	则zi.方法()调用的是子类的方法，zi.变量显示的是父类的变量
	 * 
	 */
	}
}

class Fu {

	int i = 10;

	void set() {
		i = 20;
	}

}