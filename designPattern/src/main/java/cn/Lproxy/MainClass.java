package cn.Lproxy;

public class MainClass {
	
	public static void main(String[] args) {
		// 1.被增强对象实例化
		Subject sub = new MySubject();
		// 2.代理对象实例化，传入被增强对象
		Subject proxySub = new ProxySubject(sub);
		//3.调用方法
		proxySub.say("mahao");
	}
}
