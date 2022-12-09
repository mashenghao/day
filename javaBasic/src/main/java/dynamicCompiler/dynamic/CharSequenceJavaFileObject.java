package dynamicCompiler.dynamic;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * 输入对象 源码
 *
 * @author mahao
 * @date 2022/12/08
 */
public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

    final CharSequence content;

    public CharSequenceJavaFileObject(String className, CharSequence content) {
        super(URI.create("string:///" + className.replace('.', '/') + javax.tools.JavaFileObject.Kind.SOURCE.extension), javax.tools.JavaFileObject.Kind.SOURCE);
        this.content = content;
    }

    /**
     * 提供给javaCompiler 输入回调用，用来获取待编译的源码。
     *
     * @param ignoreEncodingErrors
     * @return
     */
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return this.content;
    }

}
