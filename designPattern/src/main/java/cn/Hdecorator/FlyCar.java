package cn.Hdecorator;

public class FlyCar extends Car{
	
	private Car car;
	
	public FlyCar(Car car){
		this.car=car;
	}
	
	public void fly(){
		System.out.println("汽车能飞---");
	}
	
	public void show() {
		car.show();
		this.fly();
	}
	
	
}
