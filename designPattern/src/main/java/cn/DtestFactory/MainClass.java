package cn.DtestFactory;

import cn.DtestFactory.factory.AddOpertionFactory;
import cn.DtestFactory.factory.OpertionFactory;

import java.util.Scanner;

/**
 * 简易计算器，测试工厂模式
 * 
 * @author mahao
 *
 */
public class MainClass {

	public static void main(String[] args) {
		int num1 = 0;
		int num2 = 0;
		String str;
		Scanner sc = new Scanner(System.in);
		num1 = sc.nextInt();
		str = sc.next();
		num2 = sc.nextInt();
		
		// 》》 简单工厂模式
		if ("+".equals(str)) {
			/*
			 * 通过实例工厂，获取操作实例
			 */
			Opertion opertion = OpertionSimpleFactory.getOpertion("+");
			opertion.setNum1(num1);
			opertion.setNum2(num2);
			System.out.println("结果为: " + opertion.getResult());

		} else if ("-".equals(str)) {
			Opertion opertion = OpertionSimpleFactory.getOpertion("+");
			opertion.setNum1(num1);
			opertion.setNum2(num2);
			System.out.println("结果为: " + opertion.getResult());
		}
 
		
		// 》》 工厂模式
		if ("+".equals(str)) {
			/**
			 *  多态获取所需要的工厂实例对象
			 */
			OpertionFactory factory = new AddOpertionFactory();
			Opertion opertion = factory.getOpertion();
			opertion.setNum1(num1);
			opertion.setNum2(num2);
			System.out.println("结果为2："+opertion.getResult());
		}
	}
}
