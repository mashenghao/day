package cn.Padapter;
/**
 * 适配器，用来调用一个组件的方法，在调用过后，把数据处理后，
 * 传送给调用的组件，使其满足需要的数据形式。
 * 
 * @author maho
 *
 */
public class YaoMingAdapter extends YaoMing{
	
	public void speakAdapter(){
		this.speak();
		System.out.println("这是翻译------------");
		System.out.println("hello , i am ming");
	}
	
}
