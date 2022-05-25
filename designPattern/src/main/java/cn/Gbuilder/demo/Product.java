package cn.Gbuilder.demo;

/**
 * 产品类
 *
 * @author mahao
 * @date 2019年4月17日 上午10:49:38
 */
public class Product {
	
	private String name;
	private double price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + "]";
	}
	
	
}
