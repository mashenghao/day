package cn.Gbuilder;

/**
 * 建造者模式，将需要的创建对象，放在不同的构造者
 * 
 * 案列： 修建不同类型的房子（平房，公寓），则去选择 不同的房屋构建器，将不同房屋的类型注入到设计者中
 * 
 * 
 * @author mahao
 *
 */
public class MainClass {

	public static void main(String[] args) {
		// 1.传统方式
		House h = new House();
		h.setTop("房顶");
		h.setWall("墙");
		System.out.println(h);

		// 构建者模式
		/**
		 * 1.director类似于执行者，执行builder提供的构建建议，执行他们 的方法
		 * 2.builder相当于构建者，为对象提供构建方案，为每个对象提供独特的方案 
		 * 3.当设计者执行完建造的方法后，从构建者哪里获去具体的对象
		 */
		//1.提供构建模式
		PingFangBuilder pingFangBuilder = new PingFangBuilder();
		//2.设计者执行构建模式
		Director.makeHouse(pingFangBuilder);
		//3.得到产生的具体实例
		House house = pingFangBuilder.getHouse();
		System.out.println(house);
		
		//构建公寓对象
		GongyuBuilder gongyuBuilder = new GongyuBuilder();
		Director.makeHouse(gongyuBuilder);
		House  house2 = gongyuBuilder.getHouse();
		System.out.println(house2);
	}
}
