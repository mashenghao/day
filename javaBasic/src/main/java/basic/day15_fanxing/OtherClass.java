package basic.day15_fanxing;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

/**
 * JDK中常用类介绍
 *
 * @author mahao
 * @date: 2019年3月6日 下午8:00:02
 */
public class OtherClass {

	@Test
	public void testSystem() throws IOException {
		/*
		 * InputStream in = System.in; PrintStream out = System.out; int num =
		 * in.read(); out.println((char)num);
		 */
		// 获得系统属性信息
		Properties ps = System.getProperties();
		for (Object obj : ps.keySet()) {
			System.out.println(obj + "---" + ps.get(obj));
		}
		String va = System.getProperty("java.version");
		System.out.println("获取单个属性" + va);
		String va2 = System.setProperty("java.version", "jdk1.7");
		System.out.println("更改属性，返回值是" + va2);
		System.out.println("获取单个属性" + System.getProperty("java.version"));
	}

	@Test
	public void testRuntime() throws IOException, InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		// 运行电脑上可执行文件
		Process process = runtime.exec("C:\\Program Files (x86)\\Tencent\\QQ\\Bin\\QQScLauncher.exe");
		Thread.sleep(4000);
		// 关闭进程
		process.destroy();
	}

	@Test
	public void testDate() {
		Date date = new Date();
		System.out.println(date);
	}

	@Test
	public void testCalatar() {
		//打印2008年8月8号 和他的前一个月
		String[] mons = {"一月","二月","三月","四月"
				,"五月","六月","七月","八月"
				,"九月","十月","十一月","十二月"};
		Calendar calendar = Calendar.getInstance();
		calendar.set(2008, 7, 8); //当设置月份的时候，月份需要减-1，才能匹配上当前月
		int year = calendar.get(Calendar.YEAR);
		int index_month = calendar.get(Calendar.MONTH);
		System.out.println(index_month);//2
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(year+"年"+mons[index_month]+day+"日");//2019年8月6日
		
		//1.任意年的二月有多少天
		calendar.set(2008 , 2, 31);//设置成3月1号
		calendar.add(Calendar.DAY_OF_MONTH, -1);//3月1号前一天
		calendar.get(Calendar.DAY_OF_MONTH);//当天的几号
		//2.昨天的这个时刻
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		System.out.println(calendar.getTime());
	}
	
	@Test
	public void testMath() {
		//0<= x <1
		for(int i=0; i < 10;i++) {
			System.out.println((int)(Math.random()*10)+1);
		}
		
	}
}
