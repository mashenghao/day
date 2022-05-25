package basic.day23_socket.demo;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * ip 地址测试
 *
 * @author  mahao
 * @date:  2019年3月11日 下午8:20:52
 */
public class IPDemo {
	
	public static void main(String[] args) throws UnknownHostException {
		Inet4Address add =(Inet4Address) Inet4Address.getLocalHost();
		System.out.println(add.getHostAddress());//主机ip
		System.out.println(add.getHostName());//主机名称
	}
}
