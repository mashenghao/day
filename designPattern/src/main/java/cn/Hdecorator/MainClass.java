package cn.Hdecorator;
/**
 * 装饰模式（包装模式）
 * 
 * @author mahao
 *
 */
public class MainClass {
	
	public static void main(String[] args) {
		//1.普通的汽车、
		Car car = new Car();
		car.show();
		
		//2.增强汽车的类,车能飞
		Car flyCar = new FlyCar(car);
		flyCar.show();
		
		//3.在此基础上，继续增强，车子能游泳，能飞
		Car swimCar = new SwimCar(flyCar);
		swimCar.show();
		
	}
}
