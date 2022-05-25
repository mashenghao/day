package cn.Gbuilder;

/**
 * 房屋构建者，提供具体对象的创建方法
 * 
 * @author mahao
 *
 */
public class PingFangBuilder implements Builder{
	
	private House h = new House();
	
	@Override
	public void make1() {
		h.setWall("平方修建------------墙");
	}

	@Override
	public void make2() {
		h.setTop("平方修建------------顶");
	}

	@Override
	public House getHouse() {
		return h;
	}
	
	
}
