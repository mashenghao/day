package cn.TtemplateMethod;

public class MakeBus extends MakeCar {

	@Override
	public void makeHead() {
		System.out.println("公交车---车头");
	}

	@Override
	public void makeBody() {
		System.out.println("公交车---车身");
	}

	@Override
	public void makeTail() {
		System.out.println("公交车---车尾");
	}

}
