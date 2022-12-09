package dynamicCompiler.dynamic;

import javax.tools.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 1. 文件管理器，用来提供给JavaCompiler编译完成后，获取回调输出的位置的。 将会返回一个ByteArrayJavaFileObject
 * 将数据写入到字节数组中。
 * <p>
 * 2. 负责搜寻包下资源。
 *
 * @author mahao
 * @date 2022/12/08
 */
public class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    Map<String, ByteArrayJavaFileObject> compiledClasses = new HashMap();
    ClassLoader classLoader;

    protected ClassFileManager(StandardJavaFileManager fileManager, ClassLoader classLoader) {
        super(fileManager);
        this.classLoader = classLoader;
    }

    /**
     * 当JavaCompiler任务结束后，会去回调FileManager的这个方法，
     * 获取 className的字节码应该输出到那个输出对象中。
     *
     * @return
     */
    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        //将编译好的ByteArrayJavaFileObject 放到compiledClasses中存储。
        ByteArrayJavaFileObject o = new ByteArrayJavaFileObject(className);
        this.compiledClasses.put(className, o);
        return o;
    }





    public String inferBinaryName(Location location, JavaFileObject file) {
        return file instanceof PathJavaFileObject ? ((PathJavaFileObject) file).binaryName() : super.inferBinaryName(location, file);
    }


    public Iterable<JavaFileObject> list(JavaFileManager.Location location, String packageName, Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
        List<JavaFileObject> files = new ArrayList<>();
        for (JavaFileObject f : super.list(location, packageName, kinds, recurse))
            files.add(f);
        if (location == StandardLocation.CLASS_PATH &&
                !packageName.startsWith("java") && !packageName.startsWith("javax")) {
            String javaPackageName = packageName.replace('.', '/');
            //从自定义的类加载器中去加载资源。
            Enumeration<URL> urls = this.classLoader.getResources(javaPackageName);
            while (urls.hasMoreElements()) {
                URL packageFolderURL = urls.nextElement();
                files.addAll(listUnder(packageName, packageFolderURL));
            }
        }
        return files;
    }

    private Collection<JavaFileObject> listUnder(String packageName, URL packageFolderURL) {
        if ("bytes".equals(packageFolderURL.getProtocol())) {
            List<JavaFileObject> lists = new ArrayList<>();
            try {
                lists.add(new PathJavaFileObject(packageFolderURL.toString(), packageFolderURL.openStream()));
            } catch (Exception e) {
                throw new RuntimeException("Wasn't able to open " + packageFolderURL + " as a jar file", e);
            }
            return lists;
        }
        return Collections.emptyList();
    }
}

