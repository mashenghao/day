package cn.Gbuilder.demo3;

/**
 * 指挥者类---产品经理
 *
 * @author mahao
 * @date 2019年4月17日 下午1:09:10
 */
public class Director {
	
	public App create(String system) {
		
		return new WorkBuilder()//
				.setName("探探")//
				.setFunction("交流，玩了")//
				.setSystem(system)//
				.build();
				
	}
}
