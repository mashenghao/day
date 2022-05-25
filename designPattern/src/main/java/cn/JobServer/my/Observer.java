package cn.JobServer.my;

/**
 * 	观察者接口
 *
 */
public interface Observer {
	
	/**
	 * 执行这个方法
	 * 
	 * @param obs
	 */
	public void execute(Observable obs);
		
}
