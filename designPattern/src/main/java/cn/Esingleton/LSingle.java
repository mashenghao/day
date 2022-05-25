package cn.Esingleton;

/**
 * 懒汉式
 */
public class LSingle {
	
	private static LSingle single; 
	
	private LSingle(){
		System.out.println("私有化构造方法，只允许自己创建");
	}
	
	public static LSingle getLSingle(){
		/**
		 * 双重检查，比方法上加锁效率高
		 * 
		 * 	双重检查的发生只会是在第一次吃创建实例的时候，
		 * 当创建完成后，single就不是null了，判断进不去
		 * 里面的双重检查就不会被执行。如果是方法上加锁，第
		 * 一次效率高，但是第二次，第三次执行时候，都需要等
		 * 待
		 * 
		 */
		if (single==null){
			synchronized (LSingle.class) {
				if(single==null){
					single=new LSingle();
				}
			}
		}
		return single;
	}
}
