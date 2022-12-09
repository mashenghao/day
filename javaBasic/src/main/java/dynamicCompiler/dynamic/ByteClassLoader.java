package dynamicCompiler.dynamic;

import javax.tools.JavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.*;

/**
 * classpath改为扫描内部字节集合中的字节码文件
 *
 * @author mahao
 * @date 2022/12/08
 */
public class ByteClassLoader extends ClassLoader {

    private final Map<String, byte[]> classes = new HashMap<>();

    public void putClass(String className, byte[] byteSource) {
        classes.put(className, byteSource);
    }

    public ByteClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (!classes.containsKey(name)) {
            throw new ClassNotFoundException();
        }
        return defineClass(name, classes.get(name), 0, classes.get(name).length);
    }

    protected Enumeration<URL> findResources(String name) throws IOException {
        String pkgName = name.replace('/', '.');
        if (pkgName.endsWith(JavaFileObject.Kind.CLASS.extension))
            pkgName = pkgName.substring(0, pkgName.length() - 6);

        List<URL> filtered = new ArrayList<>();
        for (Map.Entry<String, byte[]> entry : this.classes.entrySet()) {
            String className = entry.getKey();
            //类名或者包名相同即为 匹配
            if (className.equals(pkgName) ||
                    (className.contains(".") && className.substring(0, className.lastIndexOf(".")).equals(pkgName))) {
                String urlString = "bytes:///" + entry.getKey().replace('.', '/') + JavaFileObject.Kind.CLASS.extension;
                filtered.add(new URL(null, urlString, new URLStreamHandler() {
                    protected URLConnection openConnection(URL u) {
                        return new URLConnection(null) {
                            public InputStream getInputStream() {
                                return new ByteArrayInputStream(entry.getValue());
                            }

                            public void connect() {
                            }
                        };
                    }
                }));
            }
        }

        Enumeration<URL> en = super.findResources(name);
        while (en.hasMoreElements())
            filtered.add(en.nextElement());
        return Collections.enumeration(filtered);
    }
}