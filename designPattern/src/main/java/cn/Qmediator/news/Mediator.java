package cn.Qmediator.news;

/**
 * 中介者
 *
 * @author  mahao
 * @date: 2018年11月10日 下午4:26:00
 */
public class Mediator {

	/**
	 *  * 中介者持有两个对象引用
	 */
	private Person man;
	private Person woman;

	public Person getMan() {
		return man;
	}

	public void setMan(Person man) {
		this.man = man;
	}

	public Person getWoman() {
		return woman;
	}

	public void setWoman(Person woman) {
		this.woman = woman;
	}

	/**
	 * 将实例的对象数据比较，放在中介者身上， 每个实例都有，中介者的引用，实例传入 被比较对象，调用引用的中介者，进行逻辑
	 * 判断。。。也就是将实例计较放在了中介者这儿。
	 * 
	 * @param p
	 */
	public void checkFlag(Person p) {
		/** 保存被计较的实例 */
		if (p instanceof Man) {
			this.setMan(p);
		} else {
			this.setWoman(p);
		}
		if (man == null || woman == null) {
			System.out.println("比较失败，，，数据不全");
		} else {
			if (man.getFlag() == woman.getFlag()) {
				System.out.println("匹配成功");
			} else {
				System.out.println("匹配失败");
			}
		}

	}
}
