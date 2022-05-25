package cn.KflyWeight;

/**
 * 享元模式
 * 
 * 类似于，连接池技术 
 * 
 */
public class MainClass {
	
	public static void main(String[] args) {
		ConnectPool pool = new ConnectPool();
		Connect con1 = pool.getConnect("1");
		Connect con2 = pool.getConnect("2");
		Connect con3 = pool.getConnect("1");
		Connect con4 = pool.getConnect("4");
		
		System.out.println(con1);
		System.out.println(con2);
		System.out.println(con3);
		System.out.println(con4);
		
		System.out.println(con1==con3);
	}
}	
