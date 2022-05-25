package cn.DtestFactory.factory;

import cn.DtestFactory.AddOpertion;
import cn.DtestFactory.Opertion;

public class AddOpertionFactory implements OpertionFactory{

	public Opertion getOpertion() {
		return new AddOpertion();
	}

}
