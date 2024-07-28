package dynamicCompiler.p7;

/**
 * 类的卸载
 *
 * @author mash
 */
public class Main {


    public static void main(String[] args) throws Exception {

        Thread.sleep(2000);

        RuleManager ruleManager = new RuleManager();

        new Thread(() -> {
            try {
                Thread.sleep(20_000);

                System.out.println("更新规则了");
                update(ruleManager);


                Thread.sleep(20_000);
                System.out.println("调用GC 回收掉老的规则Class");
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


        //主线程 一直接受请求，处理流水数据
        for (int i = 0; i < 10000; i++) {
            boolean audit = ruleManager.getEndStrRule().audit("str_" + i);
            if (audit) {
                System.err.println("输出字符结尾:" + i);
            }
            Thread.sleep(1000);
        }
    }

    public static void update(RuleManager ruleManager) throws Exception {
        String source = "package dynamicCompiler.p7;\n" +
                "\n" +
                "\n" +
                "import dynamicCompiler.IRule;\n" +
                "\n" +
                "public class EndStrRule implements IRule {\n" +
                "    @Override\n" +
                "    public boolean audit(String str) {\n" +
                "        return str.endsWith(\"6\");\n" +
                "    }\n" +
                "}";
        ruleManager.updateRule(source);
    }


}
