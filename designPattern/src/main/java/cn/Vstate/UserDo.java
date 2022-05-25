package cn.Vstate;

public class UserDo{
	
	private char flag;
	private State state;
	
	public UserDo(){
		state= new StateA();
	}
	public void doSomething(char c){
		this.flag= c;
		state.handle(this);
		state= new StateA();
	}
	
	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
