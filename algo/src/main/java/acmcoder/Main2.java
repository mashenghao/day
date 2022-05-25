package acmcoder;

/**
 * 豚厂给自研的数据库设计了一套查询表达式，在这个表达式中括号表示将里面的字符串翻转。请你帮助实现这一逻辑
 * <p>
 * s
 *
 * @author: mahao
 * @date: 2019/9/4
 */

import java.util.Stack;

public class Main2 {


    /*请完成下面这个函数，实现题目要求的功能
    当然，你也可以不按照下面这个模板来作答，完全按照自己的想法来 ^-^
    ******************************开始写代码******************************/

    /**
     * 一行字符串
     * <p>
     * 如果表达式括号不匹配，输出空字符串
     * <p>
     * 样例输入
     * ((ur)oi)
     * 样例输出
     * iour
     *
     * @param expr
     * @return
     */
    static String resolve(String expr) {

        StringBuilder sb = new StringBuilder();
        if (expr == null) {
            return "";
        }
        Stack<Character> stack = new Stack<>();

        for (int i = 0, n = expr.length(); i < n; i++) {
            char ch = expr.charAt(i);
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.empty()) {
                    return "";
                }
                stack.pop();
                sb.reverse();
            } else {
                sb.append((char) ch);
            }
        }

        if (stack.empty()) {
            return sb.toString();
        } else {
            return "";
        }

    }

    /******************************结束写代码******************************/


    public static void main(String[] args) {


        String res = resolve(null);
        System.out.println(res);
    }
}
