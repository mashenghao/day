package cn.Vstate;

public class StateB extends State {

	@Override
	public void handle(UserDo userDo) {
		if(userDo.getFlag()=='b'){
			System.out.println("这是B状态");
		}else{
			userDo.setState(new StateNo());
			userDo.doSomething(userDo.getFlag());
		}
	}

}
