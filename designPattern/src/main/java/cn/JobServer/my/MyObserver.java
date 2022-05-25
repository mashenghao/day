package cn.JobServer.my;
/**
 * 自定义检查者
 * 
 * @author maho
 *
 */
public class MyObserver implements Observer{

	@Override
	public void execute(Observable obs) {
		System.out.println(obs);
	}

}
