package ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 *
 *
 * java
 *
 *
 *  @author mash
 * @date 2024/7/28 下午10:43
 */
public class Main {

    public static void main(String[] args) {
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();

        PhantomReference<String> ref = new PhantomReference<>("aaaa", referenceQueue);

    }
}
