package cn.Gbuilder.demo3;

/**
 * 产品类--app探探
 *
 * @author mahao
 * @date 2019年4月17日 下午12:40:25
 */
public class App {
	
	public static final String ANDROID = "android";
	public static final String IOS = "ios";
	
	private String appName;
	private String appFuction;
	private String appSystem;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppFuction() {
		return appFuction;
	}
	public void setAppFuction(String appFuction) {
		this.appFuction = appFuction;
	}
	public String getAppSystem() {
		return appSystem;
	}
	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}
	@Override
	public String toString() {
		return "App [appName=" + appName + ", appFuction=" + appFuction + ", appSystem=" + appSystem + "]";
	}
	
}
