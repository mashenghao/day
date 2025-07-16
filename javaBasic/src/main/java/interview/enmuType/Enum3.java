package interview.enmuType;

/**
 * 所有的枚举都继承 public abstract class Enum<E extends Enum<E>> implements Comparable<E>, Serializable类。
 * <p>
 * 枚举的源码实现就是:
 * <p>
 * 每个枚举类就是一个Class，枚举项目是Class的 public static final Class SA的属性。
 * <p>
 *
 * <p>
 * <p>
 * 枚举使用练习
 */
public enum Enum3 {

    SA;
}

final class Color /*extends Enum<Color>*/ {
    public static final Color[] values() {
        return (Color[]) $VALUES.clone();
    }

    public static Color valueOf(String name) {
        //todo
        return null;
    }

    private Color(String s, int i) {

    }

    public static final Color RED;
    public static final Color BLUE;
    public static final Color GREEN;

    private static final Color $VALUES[];

    static {
        RED = new Color("RED", 0);
        BLUE = new Color("BLUE", 1);
        GREEN = new Color("GREEN", 2);
        $VALUES = (new Color[]{RED, BLUE, GREEN});
    }
}