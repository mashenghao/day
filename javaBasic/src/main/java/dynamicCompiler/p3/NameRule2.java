package dynamicCompiler.p3;

import dynamicCompiler.p2.Dog1;

/**
 * @author mahao
 * @date 2022/12/08
 */
public class NameRule2 implements IRule {

    @Override
    public boolean audit(String name) {
        if ("zs".equals(name)) {
            return true;
        }
        System.out.println(Dog1.class);
        return false;
    }
}
