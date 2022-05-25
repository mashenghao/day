package basic.day14_list.genercIterator;

import basic.day14_list.generc.Son;
import basic.day14_list.iteratorPack.MyList;

/**
 * 通过迭代器测试泛型上限和泛型下限------------无法进行测试，日后在写
 *
 * @author  mahao
 * @date:  2019年3月4日 下午4:02:45
 */
public class MainClass {
	
	public static void main(String[] args) {
		MyList<Son> list = new MyList<Son>();
		list.add(new Son());	
		list.add(new Son());	
		list.add(new Son());	
		list.add(new Son());
		
	}
}
