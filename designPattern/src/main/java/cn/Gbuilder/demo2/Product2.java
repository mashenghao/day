package cn.Gbuilder.demo2;

/**
 * 产品类
 *
 * @author mahao
 * @date 2019年4月17日 上午10:49:38
 */
public class Product2 {

	private String name;
	private double price;
	private double height;
	private double weight;

	/**
	 * 创建内部构建类，含有创建所有对象属性 注意内部类设置是静态内部类，因为这样可以在获取建造者对象 时，不需要创建在多创建product对象了
	 */
	public class Builder {

		private String name;
		private double price;
		private double height;
		private double weight;

		public Builder(Builder builder) {

		}

		public Builder() {

		}

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

		// 进行构建
		public Product2 build() {
			Product2 p = new Product2();
			p.setName(this.name);
			p.setHeight(this.height);
			p.setPrice(this.price);
			p.setWeight(this.weight);
			return p;
		}

	}

	public Product2() {
		super();
	}

	public Product2(String name, double price, double height, double weight) {
		super();
		this.name = name;
		this.price = price;
		this.height = height;
		this.weight = weight;
	}

	public Builder builder() {

		return new Builder();
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
