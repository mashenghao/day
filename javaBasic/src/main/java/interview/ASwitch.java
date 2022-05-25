package interview;

/**
 * switch使用,java基础总结，第6单元,switch执行是，当case选择到匹配项，然后向下执行，直到
 * 遇到了终止语句(bread return),default也是case选项，当其他case都没有匹配时，则会去指向default
 * 里面的语句，default执行向其他case，会一直向下执行，直到终止指令。
 *
 * @author: mahao
 * @date: 2019/8/20
 */
public class ASwitch {

    static {
        System.out.println("ASwitch 初始化 。。。");
    }
    public static void main(String[] args) {
        /*
        switch支持的数据类型有，char byte short int String enum类型以及包装类型
         */
        Integer s = Integer.valueOf(155);
        switch (s) {
            default:
                System.out.println("null");
            case 1:
                System.out.println(1);
                break;
            case 2:
                System.out.println(2);
                break;
            case 3:
                System.out.println(3);
                break;

        }
    }

    public void testChar() {
        char ch = 'a';
        switch (ch) {
            case 'a':
                System.out.println(11);
            case 'b':
                System.out.println(22);
                break;

        }
    }


    public void testString() {
        int flag = 2;
        String str = "aa";
        switch (str) {
            case "aa":
                flag = 1;
            case "bb":
                flag = 2;
                break;
        }
        /*
        public void testString() {
        int flag = false;
        String str = "aa";
        byte var4 = -1;
        switch(str.hashCode()) {
        case 3104:
            if (str.equals("aa")) {
                var4 = 0;
            }
            break;
        case 3136:
            if (str.equals("bb")) {
                var4 = 1;
            }
        }

        switch(var4) {
        case 0:
            flag = true;
        case 1:
            flag = true;
        default:
        }
    }
         */
    }


    enum ENUM {
        AA, BB, CC;
    }

    public void testEnum() {
        int num = 10;
        ENUM a = ENUM.AA;
        switch (a) {
            case AA:
                num = 11;
                break;
            case BB:
                num = 12;
                break;
        }
    }
}
