package chapter9_object;

/**
 * java枚举原理
 *
 * @author: mahao
 * @date: 2020/11/7
 */
public class EnumsExample {

    public static void main(String[] args) {
        Day[] values = Day.values();
        for (Day value : values) {
            System.out.print(value.getClass());
            System.out.println("=== " + value.toString());
            value.name();
        }
    }
}

enum Day {
    DAY1(1, "11"),
    DAY2(2, "33"),
    DAY3(3, "33");

    private int id;
    private String val;

    Day(int id, String val) {
        this.id = id;
        this.val = val;
    }
}
