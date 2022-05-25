package acmcoder;

import java.util.Scanner;

/**
 * @author: mahao
 * @date: 2019/10/14
 */
public class Ip黑名单 {


    /*请完成下面这个函数，实现题目要求的功能
    当然，你也可以不按照下面这个模板来作答，完全按照自己的想法来 ^-^
    ******************************开始写代码******************************/
    static boolean CheckBlackList(String userIP, String blackIP) {
        if (userIP.equals(blackIP)) {
            return true;
        } else if (blackIP.contains("/")) {

        }
        return false;
    }

    /******************************结束写代码******************************/


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean res;

        String _userIP;
        try {
            _userIP = in.nextLine();
        } catch (Exception e) {
            _userIP = null;
        }

        String _blackIP;
        try {
            _blackIP = in.nextLine();
        } catch (Exception e) {
            _blackIP = null;
        }

        res = CheckBlackList(_userIP, _blackIP);
        System.out.println(String.valueOf(res ? 1 : 0));
    }
}
