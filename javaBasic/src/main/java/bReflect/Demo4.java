package bReflect;

/**
 * 打印属性信息
 *
 * @author mahao
 * @date 2019年4月17日 下午9:16:25
 */
public class Demo4 {
	
	public static void main(String[] args) {
		ClassUtil.printClassField(new String());
		ClassUtil.printConstractor(String.class);
	}
}
