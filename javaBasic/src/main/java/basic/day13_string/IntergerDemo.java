package basic.day13_string;

/**
 * int类型的自动拆装箱
 *
 * @author  mahao
 * @date:  2019年3月1日 下午12:56:23
 */
public class IntergerDemo {
	
	public static void main(String[] args) {
		Integer i1 = new Integer(1);
		Integer i2 = 1;
		Integer i3 = 1;
		
		System.out.println(i1==i2);//false  因为i1和i2是两个不同的对象，比较地址值是不同的
		System.out.println(i2==i3); //true 当两个Integer比较数据，数值小于-127--128，比较的
									//是数值，这两个对象在内存中指向同一个int类型的对象，不在这个
									//范围内的时候，将重新开辟内存空间。
		System.out.println(i2+1);//这里自动拆箱功能，i2.intValue()+1 = i2+1的操作；
		
	}
}
