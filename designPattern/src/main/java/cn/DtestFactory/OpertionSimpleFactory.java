package cn.DtestFactory;

/**
 * 简单工厂方法模式
 */
public class OpertionSimpleFactory {
	/**
	 * 获取操作实例
	 * 
	 * @param type
	 * @return
	 * @author mahao
	 */
	public static Opertion getOpertion(String type){
		if("+".equals(type)){
			return new AddOpertion();
		}else if("-".equals(type)){
			return new SubOpertion();
		}
		return null;
	}

}
