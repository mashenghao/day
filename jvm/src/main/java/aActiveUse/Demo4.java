package aActiveUse;


/**
 * 数组的本质：
 * 对于数组实例类型来说，其类型是由jvm在运行期间动态生成的，表示为 [类型 这种形式，
 * 动态生成的类型，其父类型就是Object。
 * jvm创建的数组类型分为两种，
 * 一种是引用类型的数组，比如String数组，这种表示形式是[String，用的助记符创建的
 * 是anewarray助记符。
 * 另一种是，原始类型数据，比如int数组，这种形式是[I,用的助记符是newarray.
 * <p>
 * 1. Parent4 p = new Parent4(); 类会被初始化吗？
 * <p>
 * 2. Parent4[] ps = new Parent4[1];类会被初始化吗？
 */
public class Demo4 {

    public static void main(String[] args) {
        Parent4 p = new Parent4();
        System.out.println("Parent4的类类型  " + p.getClass());//aActiveUse.Parent4

        Parent4[] ps = new Parent4[1];
        System.out.println("Parent4[]的类类型  " + ps.getClass()); //[LaActiveUse.Parent4;

        //对于基本类型是
        int[] is = new int[3];
        System.out.println(is.getClass());

        String[] s = new String[3];
    }
}

class Parent4 {

    static {
        System.out.println("Parent4 被初始化");
    }
}