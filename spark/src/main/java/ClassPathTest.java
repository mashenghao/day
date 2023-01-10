import java.io.InputStream;
import java.net.URL;

/**
 * 测试读取classpath下的资源。使用classloader加载资源，要是以相对路径，相对路径是以classpath为相对开始。
 * 1.使用classloader的getResource方法，只可以读取classpath下的资源，并且是以classPath为相对路径，传的路径都是
 * 相对资源路径，如读取classpath下一个jar中的class文件，class.getClassLoader().getResourceAsStream("org/objenesis/Objenesis.class")
 * 2.使用classloader加载资源时，只能加载classpath下的资源，绝对路径不支持。
 * 3.Class的getResource()方法，就是对classloader的getResource方法的一层包装，使用resolveName()方法，将/ 开头的路径，去掉 /,
 * 将./ 开头的路径，获取到包名，作为相对路径。
 *
 * @author: mahao
 * @date: 2021/3/8
 */
public class ClassPathTest {

    public static void main(String[] args) {

        // classpath目录下,该方法最终也是调用classloader的getResource方法，把"/"去掉了。 a.txt
        URL resource = ClassPathTest.class.getResource("/a.txt");
        // classpath+该类所在路径,resolveName将name 解析成包名作为前缀 packName/XX
        URL resource1 = ClassPathTest.class.getResource("");
        // classpath目录下
        URL resource2 = ClassPathTest.class.getClassLoader().getResource("");

        System.out.println(resource);
        System.out.println(resource1);
        System.out.println(resource2);

        //获取classpath下jar包中的class文件
        InputStream resourceAsStream = ClassPathTest.class.getClassLoader().getResourceAsStream("org/objenesis/Objenesis.class");
        System.out.println(resourceAsStream);
    }
}
