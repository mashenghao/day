package cn.Obridge;

/**
 * 吉普车
 *
 * @author  mahao
 * @date:  2018年10月30日 下午1:31:31
 */
public class JeepCar extends Car {

	public JeepCar(Engine engine) {
		super(engine);
	}

	@Override
	public void makeCar() {
		System.out.println("吉普车---"
	+super.engine.installEngine());
	}

}
