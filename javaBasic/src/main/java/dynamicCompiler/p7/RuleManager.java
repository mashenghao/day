package dynamicCompiler.p7;

import dynamicCompiler.IRule;
import dynamicCompiler.dynamic.ByteClassLoader;
import dynamicCompiler.dynamic.DynamicCompiler;

import java.util.Map;

/**
 * 提供动态更新规则的能力
 *
 * @author mash
 * @date 2024/6/20
 */
public class RuleManager {

    final static String RULE_CLASS_NAME = "dynamicCompiler.p7.EndStrRule";

    final static String RULE_SOURCE = "package dynamicCompiler.p7;\n" +
            "\n" +
            "\n" +
            "import dynamicCompiler.IRule;\n" +
            "\n" +
            "public class EndStrRule implements IRule {\n" +
            "    @Override\n" +
            "    public boolean audit(String str) {\n" +
            "        return str.endsWith(\"5\");\n" +
            "    }\n" +
            "}";

    private volatile IRule endStrRule;

    public RuleManager() throws Exception {

        ByteClassLoader byteClassLoader = new ByteClassLoader();
        byte[] bytes = dynamicCompiler(RULE_CLASS_NAME, RULE_SOURCE, byteClassLoader);
        byteClassLoader.putClass(RULE_CLASS_NAME, bytes);
        endStrRule = (IRule) byteClassLoader.loadClass(RULE_CLASS_NAME).newInstance();
//        endStrRule.getClass().getClassLoader()
    }


    public IRule getEndStrRule() {
        return endStrRule;
    }

    public void updateRule(String source) throws Exception {
        ByteClassLoader byteClassLoader = new ByteClassLoader();
        byte[] bytes = dynamicCompiler(RULE_CLASS_NAME, source, byteClassLoader);
        byteClassLoader.putClass(RULE_CLASS_NAME, bytes);
        endStrRule = (IRule) byteClassLoader.loadClass(RULE_CLASS_NAME).newInstance();
    }


    private static byte[] dynamicCompiler(String className, String javaSource, ClassLoader classLoader) {
        DynamicCompiler compiler = new DynamicCompiler(classLoader);
        compiler.addJavaSource(className, javaSource);

        Map<String, byte[]> compile = compiler.compile();

        return compile.get(className);
    }
}
