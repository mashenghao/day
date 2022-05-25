package cn.Padapter;
/**
 * 适配器模式
 * 
 * 案列：翻译
 * 
 * 这是另一个应用组件
 * 
 * @author maho
 *
 */
public class MainClass {
	
	public static void main(String[] args) {
		//这是普通的调用一个应用组件，然而这个组件，只允许说英文
		//则需要一个适配器，来改变说的方式
		YaoMing ming = new YaoMing();
		ming.speak();
		
		//适配器模式，使其适合组件形式
		YaoMingAdapter adapter = new YaoMingAdapter();
		adapter.speakAdapter();
		
	}
}
