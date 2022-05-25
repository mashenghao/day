package cn.JobServer;

/**
 * 观察者模式
 *
 * @author  mahao
 * @date:  2018年11月10日 下午6:46:50
 */
public class MainClass {
	
	public static void main(String[] args) {
		MySubject sub = new MySubject();
		sub.addObserver(new MyObserver());//注册监听
		sub.setName("sfhjnlk");
		System.out.println(sub.toString());
	}
}
