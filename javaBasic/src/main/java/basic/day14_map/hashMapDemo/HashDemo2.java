package basic.day14_map.hashMapDemo;

/**
 * 测试接口嵌套；
 *
 * @author  mahao
 * @date:  2019年3月5日 下午2:09:33
 */
public class HashDemo2 {
	
	public void demo1() {
		
	}
}

interface IMap<K>{
	
	interface Entry<K>{
		void put(K k);
	}
	
	void clear(K k);
	
}

class MyMap<T> implements IMap<T>{

	public void clear(T k) {
		
	}
	
	class MyEntry<T> implements IMap.Entry<T>{

		public void put(T k) {
			
		}
	}
}