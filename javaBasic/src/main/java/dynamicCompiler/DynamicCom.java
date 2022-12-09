package dynamicCompiler;

import com.oracle.tools.packager.IOUtils;
import dynamicCompiler.dynamic.ByteClassLoader;
import dynamicCompiler.dynamic.DynamicCompiler;
import dynamicCompiler.p3.IRule;

import java.io.File;
import java.util.Map;

/**
 * 动态编译测试
 *
 * @author mahao
 * @date 2022/12/08
 */
public class DynamicCom {

    final static String RULE_CLASS_NAME = "dynamicCompiler.p3.NameRule";

    final static String RULE_SOURCE = "package dynamicCompiler.p3;\n" +
            "\n" +
            "import dynamicCompiler.p2.Dog;\n" +

            "public class NameRule implements IRule {\n" +
            "\n" +
            "    @Override\n" +
            "    public boolean audit(String name) {\n" +
            "        if (\"zs\".equals(name)) {\n" +
            "            return true;\n" +
            "        }\n" +
            "        System.out.println(Dog.class);\n" +
            "        System.out.println(123);\n" +
            "        return false;\n" +
            "    }\n" +
            "}\n";


        public static void main(String[] args) throws Exception {

            //1.交由字节数组加载器去加载这个类，因为源码中用到了Dog，而这个Dog类系统类加载器扫描不到，所以交由byteClassLoader，
            //当编译源码时，编译器发现这个类，就能通过byteClassLoader找到。
            ByteClassLoader byteClassLoader = new ByteClassLoader();
            byteClassLoader.putClass("dynamicCompiler.p2.Dog", IOUtils.readFully(new File("C:\\Users\\bangsun\\Desktop\\动态编译\\classes\\dynamicCompiler\\p2\\Dog.class")));

            //1. 编译为字节码
            byte[] bytes = dynamicCompiler(RULE_CLASS_NAME, RULE_SOURCE, byteClassLoader);

            //生成的字节码，放到map中，用来获取class实例
            byteClassLoader.putClass(RULE_CLASS_NAME, bytes);

            //获取class 对象
            Class<?> aClass = Class.forName(RULE_CLASS_NAME, true, byteClassLoader);
            IRule rule = (IRule) aClass.newInstance();

            boolean isZs = rule.audit("zs");
            System.out.println("isZs:  " + isZs);
            System.out.println("-----------------------");
            boolean isLs = rule.audit("ls");
            System.out.println("isLs:  " + isLs);
        }

        private static byte[] dynamicCompiler(String className, String javaSource, ClassLoader classLoader) {
            DynamicCompiler compiler = new DynamicCompiler(classLoader);
            compiler.addJavaSource(className, javaSource);

            Map<String, byte[]> compile = compiler.compile();

            return compile.get(className);
        }


}
