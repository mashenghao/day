package cn.Gbuilder.demo3;

/**
 * 实际应用 ：探探
 *
 * @author mahao
 * @date 2019年4月17日 下午12:31:22
 */
public class MainClass {

	// 客户端--客户
	public static void main(String[] args) {
		// 客户： 要一个iso系统的探探app功能是交友
		// 产品经理：分析得出就做一个探探
		// 技术主管：appName:探探，系统：ios，android功能：摇一摇找妹子
		Director director = new Director();
		App app = director.create(App.ANDROID);
		System.out.println(app);
		
		/*
		 * 变异的建造者可以只保留具体的建造者，指挥者和抽象建造者都可以不使用了
		 */
		//程序员单干
		App app2 = new WorkBuilder()//
				.setName("程序员探探")//
				.setFunction("程序员之间交流使用")//
				.setSystem(App.IOS)//
				.build();
		System.out.println(app2);
	}
}
