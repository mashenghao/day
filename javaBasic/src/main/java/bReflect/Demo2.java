package bReflect;

/**
 * 动态加载 静态加载<br>
 * 
 * 动态加载: 程序在编译时无法确定类的类型，在运行时才确定类对象，这使将所需要的类加载到程序中 成为动态加载；<br>
 * 静态加载：程序在编译时，将所有有可能需要的对象，加载到程序中。<br>
 * new就是静态加载；
 *
 * @author mahao
 * @date 2019年4月17日 下午8:14:07
 */
public class Demo2 {

	/**
	 * 用run表示一个正在运行的程序，run通过获取args的类类型， 类类型创建实例，调用里面的方法进行工作
	 * 
	 * @param args
	 * @throws Exception
	 */
	public void run(String args) throws Exception {
		Class clazz = Class.forName(args);
		Worker worker = (Worker) clazz.newInstance();
		worker.work();
	}

	public static void main(String[] args) throws Exception {
		new Demo2().run("bReflect.DesignWorker");
	}
}

interface Worker {
	void work();
}

class UIWorker implements Worker {
	public void work() {
		System.out.println("UI~~~~~work------");
	}
}

class DesignWorker implements Worker {
	public void work() {
		System.out.println("Designer~~~~~work------");
	}
}