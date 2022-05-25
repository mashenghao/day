package cn.Vstate;

/**
 * 状态模式
 *
 * @author  mahao
 * @date:  2018年11月13日 下午4:50:50
 */
public class MainClass {
	
	public static void main(String[] args) {
		UserDo userdo = new UserDo();
		userdo.doSomething('a');
		userdo.doSomething('b');
		userdo.doSomething('a');
	}
}
