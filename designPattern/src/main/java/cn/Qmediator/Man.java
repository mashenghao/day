package cn.Qmediator;

public class Man extends Person {

	public Man(String name, int flag) {
		super(name, flag);
	}

	@Override
	public void checkFlag(Person p) {
		if (p instanceof Man) {
			System.out.println("同性---");
		} else {
			if (p.getFlag() == this.getFlag()) {
				System.out.println("匹配成功----");
			} else {
				System.out.println("匹配失败----");
			}
		}
	}

}
