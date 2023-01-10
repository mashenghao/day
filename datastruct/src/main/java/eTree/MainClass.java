package eTree;

/**
 * 二叉树
 *
 * @author mahao
 * @date: 2019年3月22日 下午6:47:37
 */
public class MainClass {

	public static void main(String[] args) {
		String str = "ABC##DE#G##F###";
		BiTree t = new BiTree(str);
		System.out.print("非递归中序遍历 --");
		t.printfCenter();
		System.out.println();
		System.out.print("非递归先序遍历 --");
		t.eachFirstr();
		System.out.println();
		System.out.print("非递归后序遍历 --");
		t.eachLast();
	}
}
