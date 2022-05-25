package cn.Qmediator;

/**
 *	未使用中介者模式
 * 
 * @author mahao
 * @date: 2018年11月10日 下午12:24:25
 */
public class MainClass {

	/**相亲匹配*/
	public static void main(String[] args) {
		Person zs = new Man("张三", 1);
		Person ls = new Man("李四",2);
		Person xh = new Woman("小红", 2);
		
		zs.checkFlag(ls);
		ls.checkFlag(xh);
	}
}
