package cn.TtemplateMethod;

/**
 * 	模版方法模式
 *
 * @author  mahao
 * @date:  2018年11月11日 下午3:34:00
 */
public class MainClass {
	
	public static void main(String[] args) {
		MakeCar bus = new MakeBus();
		//1.传统方式
		bus.makeHead();
		bus.makeBody();
		bus.makeTail();
		
		//使用模版方法模式
		bus.make();
		
		//3.也利于扩展，不如新制造jeep
		MakeCar jeep = new Jeep();
		jeep.make();
	}
}
