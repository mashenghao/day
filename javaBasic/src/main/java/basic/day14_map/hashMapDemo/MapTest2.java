package basic.day14_map.hashMapDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * map练习
 *
 * 统计 字符出现个数
 *
 * @author mahao
 * @date: 2019年3月5日 下午7:36:00
 */
public class MapTest2 {
	
	@Test
	public void demo2() {
		String s = "kasbfjk;abjbjdffklfj";
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		char[] chs = s.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			int value = map.get(chs[i]) == null ? 0 : map.get(chs[i]).intValue();
			map.put(chs[i], ++value);
		}
		System.out.println(map);
	}
	
	@Test
	public void demo3() {
		String s = "kasbfjk;abjbjdffklfj";
		Map<Character, Integer> map = new TreeMap<Character, Integer>();
		char[] chs = s.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			int value = map.get(chs[i]) == null ? 0 : map.get(chs[i]).intValue();
			map.put(chs[i], ++value);
		}
		System.out.println(map);
	}
}
