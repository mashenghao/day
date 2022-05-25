package cn.Gbuilder.demo2;

import org.junit.Test;

/**
 * 构建者模式的变种使用：常用。
 * 
 * @author mahao
 * @date 2019年4月17日 上午11:08:31
 */
public class MainClass {

	// 普通模式
	@Test
	public void testConstract() {
		/**
		 * 普通模式下创建一个Product对象，需要通过构造方法，传入指定参数<br>
		 * 这种方式下读取不便，阅读不便
		 */
		Product p = new Product("桌子", 100, 12.5, 18.96);
		System.out.println(p);
	}

	// 建造者模式
	@Test
	public void testBuilder() {
		Product product = new Product.Builder()//
				.setName("mahao")//
				.setPrice(10.5)//
				.setHeight(18.5)//
				.setWeight(20)//
				.build();
		System.out.println(product);
	}

	// 建造者模式2--不使用静态内部类
	@Test
	public void testBuilder2() {
		Product2 product = new Product2();//是两个不同的对象，采用第一种方式
		Product2 product2 = product.builder()//
				.setName("mahao")//
				.setPrice(10.5)//
				.setHeight(18.5)//
				.setWeight(20)//
				.build();
		System.out.println(product);
		System.out.println(product == product2);
	}
}
