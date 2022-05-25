package cn.Gbuilder.demo;

/**
 * 具体构建者
 *
 * @author mahao
 * @date 2019年4月17日 上午10:55:35
 */
public class BaseBuilder extends Builder {

	private Product p = new Product();

	@Override
	public void setPart() {
		p.setName("基本顺序构建");
		p.setPrice(10.2);
	}

	@Override
	public Product build() {

		return p;
	}

}
