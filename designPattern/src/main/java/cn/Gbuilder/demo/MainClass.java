package cn.Gbuilder.demo;

/**
 * 建造者模式的使用--最基本用法<br>
 * 
 * 在对复杂的对象进行初始化时，如果使用构造方法初始化对象，<br>
 * 太过复杂，将会涉及很多方法调用。建造者模式将对象的构建<br>
 * 分离出来，将复杂的构建逻辑和顺序，放在不同的构建类中，将<br>
 * 构建和对象更好的解耦使用。<br>
 *
 * @author mahao
 * @date 2019年4月17日 上午10:30:31
 */
public class MainClass {
	public static void main(String[] args) {
		Director d = new Director();
		Product product = d.create();
		System.out.println(product); // Product [name=基本顺序构建, price=10.2]
	}
}
