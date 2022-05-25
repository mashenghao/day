package cn.Gbuilder.demo3;

/**
 * 具体构建者---程序员
 *
 * @author mahao
 * @date 2019年4月17日 下午1:06:48
 */
public class WorkBuilder extends Builder {

	private App app = new App();
	// 两种方式二选择一，推荐第二种，更加节省资源
	private InnerApp innerApp = new InnerApp();

	@Override
	public Builder setName(String name) {
		app.setAppName(name);
		innerApp.setAppName(name);
		return this;
	}

	@Override
	public Builder setFunction(String function) {
		app.setAppFuction(function);
		innerApp.setAppFuction(function);
		return this;
	}

	@Override
	public Builder setSystem(String system) {
		app.setAppSystem(system);
		innerApp.setAppSystem(system);
		return this;
	}

	@Override
	public App build() {
		App app2 = new App();
		app2.setAppName(innerApp.getAppName());
		app2.setAppFuction(innerApp.getAppFuction());
		app2.setAppSystem(innerApp.getAppSystem());

		return app;// app2;
	}

	/**
	 * 创建内部类，作为产品缓冲类，用这个保存数据的字段，好处是<br>
	 * 如果产品类是重量级类，消耗资源多。这个缓冲类只持有内部字段数据<br>
	 * 不会太消耗资源。<br>
	 * 
	 * 
	 */
	private class InnerApp {

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

	}
}
