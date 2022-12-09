package dynamicCompiler.dynamic;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * javaCompiler 编译完成后， 获取要写出的流。
 *
 * @author mahao
 * @date 2022/12/08
 */
public class ByteArrayJavaFileObject extends SimpleJavaFileObject {

    final ByteArrayOutputStream os = new ByteArrayOutputStream();

    ByteArrayJavaFileObject(String name) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
    }

    byte[] getBytes() {
        return this.os.toByteArray();
    }

    /**
     * JavaCompiler 会去获取到这个输出流，将编译后的字节码流写到OutputStream。
     *
     * @return
     */
    public OutputStream openOutputStream() {
        return this.os;
    }
}
