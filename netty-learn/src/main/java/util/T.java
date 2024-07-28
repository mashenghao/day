package util;

/**
 *
 *
 * @author mash
 * @date 2024/7/6 下午4:52
 */
public class T {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void print(Object obj) {
        System.err.println("[" + Thread.currentThread().getName() + "] : " + obj.toString());
    }
}
