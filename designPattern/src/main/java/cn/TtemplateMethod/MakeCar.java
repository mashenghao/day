package cn.TtemplateMethod;

/**
 * 模版方法模式必须定义一个抽象方法模版，限制
 *  调用顺序与要调用的方法接口
 *
 * @author mahao
 * @date: 2018年11月11日 下午3:40:09
 */
public abstract class MakeCar {

	public abstract void makeHead();

	public abstract void makeBody();

	public abstract void makeTail();
	
	/**
	 * 模版方法，调用顺序方法。
	 * 子类会继承这个类，实现里面的抽象方法，因而
	 * 该方法的调用将是子类的调用
	 */
	public void make() {
		this.makeHead();
		this.makeBody();
		this.makeTail();
	}
}
