import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * @author: mahao
 * @date: 2021/1/18
 */
public class URL1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL url = new URL("file:///C:\\Users\\bangsun\\Desktop\\shell.md");
        InputStream inputStream = url.openStream();
        System.out.println(new String(IOUtils.toByteArray(inputStream)));
        URL[] array = (URL[]) Arrays.asList(new URL("file:///C:/work/javaSe/out/production/javaSe/interview/ASwitch.class")).toArray();
        URLClassLoader loader = new URLClassLoader(array);
        Class<?> aClass = loader.loadClass("interview.ASwitch");
        aClass.newInstance();
    }
}
