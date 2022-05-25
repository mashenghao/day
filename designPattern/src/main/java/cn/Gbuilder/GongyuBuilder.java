package cn.Gbuilder;

/**
 * 公寓构建器
 * @author mahao
 *
 */
public class GongyuBuilder implements Builder{

	private House h = new House();
	
	@Override
	public void make1() {
		h.setWall("公寓-------墙");
	}

	@Override
	public void make2() {
		h.setTop("公寓------顶");
	}

	@Override
	public House getHouse() {
		return h;
	}

}
