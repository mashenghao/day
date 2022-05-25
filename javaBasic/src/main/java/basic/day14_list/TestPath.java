
package basic.day14_list;

import java.util.ArrayList;
import java.util.List;

public class TestPath {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("aa");
		list.add("aa");
		list.add("aa");
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			System.out.println(s);
			if (s.equals("aa")) {
				list.remove(s);
			}
			System.out.println(list);
		}
		System.out.println(list);
	}

}
