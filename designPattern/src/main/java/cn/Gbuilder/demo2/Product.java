package cn.Gbuilder.demo2;

/**
 * 产品类，使用构建者模式创建
 *
 * @author mahao
 * @date 2019年4月17日 上午10:49:38
 */
public class Product {

	private String name;
	private double price;
	private double height;
	private double weight;

	/**
	 * 创建内部构建类，含有创建所有对象属性
	 * 注意内部类设置是静态内部类，因为这样可以在获取建造者对象
	 * 时，不需要创建在多创建product对象了
	 */
	static class Builder {

		private String name;
		private double price;
		private double height;
		private double weight;

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setPrice(double price) {
			this.price = price;
			return this;
		}

		public Builder setHeight(double height) {
			this.height = height;
			return this;
		}

		public Builder setWeight(double weight) {
			this.weight = weight;
			return this;
		}
		
		//进行构建
		public Product build() {
			Product p = new Product();
			p.setName(this.name);
			p.setHeight(this.height);
			p.setPrice(this.price);
			p.setWeight(this.weight);
			return p;
		}

	}

	public Product() {
		super();
	}

	public Product(String name, double price, double height, double weight) {
		super();
		this.name = name;
		this.price = price;
		this.height = height;
		this.weight = weight;
	}

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

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", height=" + height + ", weight=" + weight + "]";
	}

}
