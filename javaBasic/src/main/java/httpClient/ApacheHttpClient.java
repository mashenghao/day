package httpClient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * connect timeout 错误
 *
 * read timeout 错误
 *
 * @author mash
 * @date 2024/8/30 下午2:07
 */
public class ApacheHttpClient {

    public static void main(String[] args) throws Exception {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(200, TimeUnit.SECONDS);
        connectionManager.setMaxTotal(3);
        connectionManager.setDefaultMaxPerRoute(2);

        // 创建带有自定义连接管理器的 HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                HttpGet httpGet = new HttpGet();
                httpGet.setURI(URI.create("https://www.baidu.com"));
                try {
                    CloseableHttpResponse execute = httpClient.execute(httpGet);
                    Thread.sleep(1000);
                    EntityUtils.toByteArray(execute.getEntity());
                    System.out.println(Thread.currentThread().getName() + "  1111111 ");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }).start();

            new Thread(() -> {
                HttpGet httpGet = new HttpGet();
                httpGet.setURI(URI.create("https://www.getui.com"));
                try {
                    CloseableHttpResponse execute = httpClient.execute(httpGet);
                    System.out.println(Thread.currentThread().getName() + "  gt ");
                    Thread.sleep(1000);
                    EntityUtils.toByteArray(execute.getEntity());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }).start();
        }

        Thread.sleep(3000);
        System.out.println(httpClient.getConnectionManager().toString());

//        Thread.sleep(20_000);
//        httpClient.getConnectionManager().closeExpiredConnections();

        for (int i = 0; i < 10; i++) {

                HttpGet httpGet = new HttpGet();
                httpGet.setURI(URI.create("https://www.bilibili.com/"));
                try {
                    CloseableHttpResponse execute = httpClient.execute(httpGet);
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " 33333  ");
                    EntityUtils.toByteArray(execute.getEntity());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

        }

        System.out.println(httpClient.getConnectionManager().toString());


        Thread.sleep(9999000);
    }
}
