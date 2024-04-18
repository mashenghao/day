package gc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * G1的一些测试日志
 *
 * @author mash
 * @date 2024/4/17 下午10:19
 */
public class G1 {


    @Test
    public void testGc() {
        System.out.println(123);
        List<byte[]> aa = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<byte[]> arrayList = new ArrayList<>();
            for (int j = 0; j < 10000; j++) {
                byte[] bytes = new byte[1024 * 2];
                byte[] byte2 = new byte[1024 * 3];
                byte[] byte3 = new byte[1024 * 4];
                byte[] byte4 = new byte[1024 * 5];
                byte[] byte100 = new byte[1024 * 100];
                byte[] byte5 = new byte[1024 * 6];
                arrayList.add(byte100);
                arrayList.add(byte5);
            }
            aa.add(new byte[1024 * 100]);
        }


        System.gc();

    }
}
