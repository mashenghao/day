package cn.DtestFactory.factory;

import cn.DtestFactory.Opertion;

/**
 * 抽象工厂模式(多态特性，将实例的创建放在，子类中)
 * 
 * @author mahao
 *
 */
public interface OpertionFactory {
	
	Opertion getOpertion();
}
