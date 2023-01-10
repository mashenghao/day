package bLinkedList;

/**
 * 链表
 *
 * @author mahao
 * @date: 2019年3月18日 下午10:33:01
 */
public class MainClass {

	public static void main(String[] args) {

		LinkedList<Integer> list = new LinkedList<>();
		Integer[] arr = { 1, 5, 89, 488 };
		list.initTail(arr);
		list.add(15);
		System.out.println(list);
		
		/*for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
			Integer val = it.next();
			System.out.print(val+" ");
		}*/
		/*System.out.println();
		list.buildAZ();*/
		System.out.println(list.set(5,2));
		System.out.println(list);
	}
}
