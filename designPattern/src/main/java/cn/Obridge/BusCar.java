package cn.Obridge;

/**
 * 公交车
 *
 * @author  mahao
 * @date:  2018年10月30日 下午4:13:28
 */
public class BusCar extends Car {

	public BusCar(Engine engine) {
		super(engine);
	}

	@Override
	public void makeCar() {
		System.out.println("公交车--"
				+super.engine.installEngine());
	}

}
