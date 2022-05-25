package basic.day24_IE.url;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class URLDemo {
	
	@Test
	public void demo1() throws IOException {
		URL url = new URL("");
		URLConnection conn = url.openConnection();
		conn.getInputStream();
		conn.getOutputStream();
	}
}
