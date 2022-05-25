package basic.day05_class;

/**
 * 单例模式： 饿汉式
 */
public class Single2 {

	private Single2() {
	};

	private static Single2 s = null;

	/* 饿汉式线程不安全 */
	public static Single2 getInstance() {
		
		if (s == null) {
			synchronized (Single2.class) {
				if (s == null) {
					s = new Single2();
				}
			}
		}
		return s;
	}
}
