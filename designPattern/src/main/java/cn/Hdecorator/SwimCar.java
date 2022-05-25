package cn.Hdecorator;

public class SwimCar extends Car{
	
	private Car car;
	
	public SwimCar(Car car){
		this.car=car;
	}
	
	public void swim(){
		System.out.println("车能游泳---");
	}
	
	public void show() {
		car.show();
		this.swim();
	}
}
