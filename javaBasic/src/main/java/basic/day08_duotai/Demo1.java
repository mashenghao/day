package basic.day08_duotai;

/**
 * 多态--转型
 * 1.父类引用接收子类对象，类型提升，向上转型 
 * 2.如果想要调用学生的特有方法时，强制将父类的引用
 * 	转为子类类型，称为向下转型；
 * 3.不能强制将父类对象，强转为子类引用，否则会抛出装换异常
 * 4.多态自始至终都是子类对象在做着变化，不可以将父类多态变化。
 *      
 * @author mahao 
 * @date 2019年1月8日 下午4:12:23
 */
public class Demo1 {
	
	public static void main(String[] args) {
		
		// >> 1.父类引用接收子类对象，类型提升，向上转型 
		Person p = new Student();
		p.getName();
		
		//  >>2.如果想要调用学生的特有方法时，强制将父类的引用
		//  转为子类类型，称为向下转型；
		Student s = (Student) p;
		s.getNo();
		
		//××××× 3.不能强制将父类对象，强转为子类引用，否则会抛出装换异常 ×××××
		Person p2 = new Person();
		//Student s2 = (Student) p2;
		//s2.getName();
		
	}
}
