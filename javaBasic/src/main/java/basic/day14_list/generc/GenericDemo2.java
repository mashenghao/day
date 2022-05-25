package basic.day14_list.generc;

import basic.day14_list.setDemo.User;

/**
 * 泛型测试二 
 *
 * @author  mahao
 * @date:  2019年3月3日 下午8:38:01
 */
public class GenericDemo2 {
	
	public static void main(String[] args) {
		//方式一,实现类操作类型确定
		GenericInteImpl g1 = new GenericInteImpl();
		//GenericInte<String> g1 = new GenericInteImpl();
		//Object r1  = g1.getT(new User("jnf"));//报错，只能存放字符串
		String r1 = g1.getT("只能传入字符串");
		System.out.println(r1);
		
		//方式二,实现类操作类型不确定
		GenericInte<String> g2 = new GenericInteImpl2<String>();
		System.out.println(g2.getT("字符串泛型"));
		
		GenericInte<User> g3 = new GenericInteImpl2<User>();
		System.out.println(g3.getT(new User("对象类型")));
	}
	
	
}

/** 泛型定义在接口上*/
interface GenericInte<T>{
	T getT(T t);
}

/**
 * 1. 泛型接口实现方式一： 如果知道实现类要操作的对象，在定义类的时候，
 * 将接口的泛型声明出来，则类的操作泛型对象就确定了
 */
class GenericInteImpl implements GenericInte<String>{
	@Override
	public String getT(String t) {
		return t;
	}
	
}

/**
 * 2. 泛型接口实现方式二： 如果不知道实现类要操作的具体对象，在定义类的时候，
 * 实现类也定义和接口一样的泛型，当对象创建时，在确定具体的操作对象；
 */
class GenericInteImpl2<T> implements GenericInte<T>{
	@Override
	public T getT(T t) {
		return t;
	}
	
}

