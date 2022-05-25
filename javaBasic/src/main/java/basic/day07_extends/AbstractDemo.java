package basic.day07_extends;

/**
 *1.抽象方法的类必须是抽象类，
 *2.抽象类不能被实例化，因为含有未实现的方法。
 *3.抽象方法被使用，必须由子类全部实现后，才可以使用。
 *
 * @author mahao 
 * @date 2019年1月5日 下午7:49:49
 */
class AbstractDemo {
	
	public static void main(String[] args) {
		Student s  = new Student();
		s.show2();
	}
}

/**抽象父类，含有抽象方法，则必须声明为抽象类*/
abstract class Person{
	
	/**抽象方法，自我介绍*/
	public abstract void show();
	
	abstract void show2();
}

class Student extends Person{

	@Override
	public void show() {
		System.out.println("我是学生");
	}

	@Override
	void show2() {
		// TODO Auto-generated method stub
		
	}
	
}