package basic.day08_duotai;

/**
 * 多态--多态的特点
 *      
 * @author mahao 
 * @date 2019年1月8日 下午7:24:33
 */
public class Demo2 {
	
	public static void main(String[] args) {
		
		//1.父类和子类含有相同的成员变量时
		//在多态中，成员变量参考左边类（引用型变量所属的类）
		Person p = new Student();
		Student s = new Student();
		System.out.println(p.num);//5
		System.out.println(s.num);//8
		
		//2.在静态方法中，子类，父类含有相同的方法
		//在多态中，静态方法参考左边类（引用型变量所属的类）
		p.doSome();//fu---------
		s.doSome();//zi----------
		
		p.getName();
	}
}
