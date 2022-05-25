package cn.Wlistener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainClass {

	public static void main(String[] args) {
		/*
		 * Button b = new Button(); b.addActionListener(new MyListener());
		 */

		MyButton myButton = new MyButton();
		myButton.addClickListener(new Listener() {

			public void click(ClickEvent e) {
				System.out.println("自定义事件被点击了");
			}
		});

		myButton.click();// 触发点击函数
	}
}

class MyListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		System.out.println("执行");
		Button bu = new Button();
		bu.addActionListener(this);
	}

}