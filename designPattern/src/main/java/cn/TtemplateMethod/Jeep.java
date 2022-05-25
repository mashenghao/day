package cn.TtemplateMethod;

public class Jeep extends MakeCar{

	@Override
	public void makeHead() {
		System.out.println("吉普-----车头");
		
	}

	@Override
	public void makeBody() {
		System.out.println("吉普-----车身");		
	}

	@Override
	public void makeTail() {
		System.out.println("吉普-----车尾");		
	}

}
