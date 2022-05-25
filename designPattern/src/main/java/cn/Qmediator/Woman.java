package cn.Qmediator;

public class Woman extends Person{

	public Woman(String name, int flag) {
		super(name, flag);
	}

	@Override
	public void checkFlag(Person p) {
		if(p instanceof Woman){
			System.out.println("同性---");
		}else{
			if (p.getFlag()==this.getFlag()){
				System.out.println("匹配成功----");
			}else{
				System.out.println("匹配失败----");
			}
		}
	}

}
