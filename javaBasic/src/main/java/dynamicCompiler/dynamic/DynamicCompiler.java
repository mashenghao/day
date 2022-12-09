package dynamicCompiler.dynamic;


import javax.tools.*;
import java.util.*;

/**
 * 1.
 * 对{@link JavaCompiler}的封装，调用 {@code addJavaSource(String className, String content)}添加源码，<br>
 * 调用 {@code compile()} 返回编译成功后的字节码字节数组。
 * <p>
 * 如果等待编译的源码中使用到了依赖类，使用默认类加载器找不到的话，需要构造函数传进来一个ClassLoader，用来搜找依赖类。
 * <p>
 * <p>
 * 2. JavaCompiler 使用
 * JavaCompiler 是由java编写的一个编译器，负责将.java 编译成 .class 。
 * JavaCompiler输入是一组{@link JavaFileObject},内部方法 CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException;
 * 会被JavaCompiler回调，用来获取等待编译的源码。 所以，我们需要自定义实现getCharContent()方法，  让获取的源码从我们字符串中读取。
 * <p>
 * 输出也是JavaFileObject，内部方法OutputStream openOutputStream() throws IOException; 当编译后，JavaCompiler回调获取编译好后字节码输出的流。
 * 因此需要我们自定义输出流，改为将字节码输出到字节数组中。
 * <p>
 * <p>
 * 3. JavaFileManager
 * 负责管理 输入JavaFileObject 和输出 JavaFileObject.
 * <p>
 * 4. DiagnosticCollector
 * 可获取编译过程中的问题
 *
 * @author mahao
 * @date 2022/12/08
 */
public class DynamicCompiler {

    JavaCompiler compiler;
    ClassFileManager manager;
    //等待编译的源码
    final Map<String, CharSequenceJavaFileObject> javaSources;
    private DiagnosticCollector<JavaFileObject> diagnostics;

    public DynamicCompiler() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public DynamicCompiler(ClassLoader loader) {
        this.javaSources = new HashMap(10);
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.manager = new ClassFileManager(this.compiler.getStandardFileManager(null, null, null), loader);
    }

    /**
     * 将源码添加到map中。
     *
     * @param className
     * @param content
     */
    public void addJavaSource(String className, String content) {
        this.javaSources.put(className, new CharSequenceJavaFileObject(className, content));
    }

    public Map<String, byte[]> compile() {
        this.diagnostics = new DiagnosticCollector();
        JavaCompiler.CompilationTask task = this.compiler.getTask(
                null,
                this.manager,
                this.diagnostics,
                Arrays.asList("-source", "1.8", "-target", "1.8", "-encoding", "UTF-8", "-XDuseUnsharedTable=true"),
                null,
                this.javaSources.values()); //java 源码

        //阻塞 等待编译完成
        task.call();

        Map<String, byte[]> classesMap = new HashMap(this.manager.compiledClasses.size());
        for (Map.Entry<String, ByteArrayJavaFileObject> en : manager.compiledClasses.entrySet()) {
            classesMap.put(en.getKey(), en.getValue().getBytes());
        }
        return classesMap;
    }

    public List<Tuple<String, Long, Long>> getDiagnostics() {
        List<Tuple<String, Long, Long>> errorList = new ArrayList();
        Iterator var2 = this.diagnostics.getDiagnostics().iterator();
        while (var2.hasNext()) {
            Diagnostic<? extends JavaFileObject> d = (Diagnostic) var2.next();
            if (d.getKind().equals(Diagnostic.Kind.ERROR)) {
                Tuple<String, Long, Long> t = new Tuple((d.getSource()).getName() + "::" + d.getMessage((Locale) null), d.getLineNumber(), d.getColumnNumber());
                errorList.add(t);
            }
        }

        return errorList;
    }


    public class Tuple<K, V1, V2> {
        private K key;
        private V1 value1;
        private V2 value2;

        public Tuple(K key, V1 value1, V2 value2) {
            this.key = key;
            this.value1 = value1;
            this.value2 = value2;
        }

        public Tuple() {
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "key=" + key +
                    ", value1=" + value1 +
                    ", value2=" + value2 +
                    '}';
        }
    }
}
