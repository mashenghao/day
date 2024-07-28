//package dynamicCompiler.p5;
//
//import dynamicCompiler.IRule;
//
///**
// * @author mash
// */
//public class Rule implements IRule {
//    /**
//     * 校验custNo是否符合指定规则
//     */
//    @Override
//    public boolean audit(String custNo) {
//        if (custNo == null || "".equals(custNo)) {
//            return false;
//        }
//        if ("1".equals(custNo)) {
//            return true;
//        }
//        if ("2".equals(custNo)) {
//            return true;
//        }
//        return false;
//    }
//}