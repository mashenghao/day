package cn.Vstate;

public class StateA extends State{

	public void handle(UserDo userDo) {
		if(userDo.getFlag()=='a'){
			System.out.println("这是A状态处理");
		}else{
			userDo.setState(new StateB());
			userDo.doSomething(userDo.getFlag());
		}
	}

}
