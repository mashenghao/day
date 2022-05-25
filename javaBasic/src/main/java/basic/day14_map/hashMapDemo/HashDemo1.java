package basic.day14_map.hashMapDemo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import basic.day14_map.Parent;

public class HashDemo1 {
	
	/**
	 * 基本api介绍
	 */
	@Test
	public void demo1() {
		Map<String,Parent> map = new HashMap<String,Parent>();
		//Hashtable不能的key和value不能为空
		map.put("1", new Parent("001"));
		map.put("2", new Parent("002"));
		System.out.println(map.put("1", new Parent("003")));
		//1.遍历，Collection<Parent> = map.values();
		Collection<Parent> values = map.values();
		for(Iterator<Parent> it = values.iterator();it.hasNext();) {
			System.out.println(it.next());
		}
		//2. Set<K> map.keySet(); 
		Set<String> keySet= map.keySet();
		for(String key : keySet) {
			System.out.println("key="+key+"--value="+map.get(key));
		}
		//3.Set<Map> map.entiySet();
		 Set<Map.Entry<String,Parent>> entrySet = map.entrySet();
		 for(Map.Entry e : entrySet) {
			 System.out.println(e.getKey()+"----"+e.getValue());
		 }
	}
}
