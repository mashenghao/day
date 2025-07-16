package jmx.hello;

import org.jolokia.jvmagent.client.AgentLauncher;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 将注册好的MBean注册到MBeanServer中。
 *
 * @author mash
 * @date 2023/9/3 14:59
 */
public class MainMBeanServer {

    public static void main(String[] args) throws Exception {
        //1. 先获取到MBeanServer
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        try {
            //2. 给注册的bean设置唯一的bean 的name
            ObjectName objectName = new ObjectName("cn.mh.jmx:type=User,name=userMbean01");

            //3.注册进去，参数MBean 和 bean的名字
            User user01 = new User();
            mBeanServer.registerMBean(user01, objectName);
            mBeanServer.registerMBean(new User(), new ObjectName("cn.mh.jmx:type=User,name=userMbean02"));

            //1. get方法 获取mbean的元数据
            MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(new ObjectName("cn.mh.jmx:type=User,name=userMbean01"));

            //2. set方法执行
            AttributeList objects = new AttributeList();
            objects.add(new Attribute("Name", "zs100"));
            AttributeList result = mBeanServer.setAttributes(objectName, objects);
            System.out.println(result);

            //3. get 获取mbean属性值
            AttributeList attributes = mBeanServer.getAttributes(objectName, new String[]{"Name", "Phone"});
            System.out.println(attributes);

            //4. 执行方法
            mBeanServer.invoke(objectName, "say", new Object[0], new String[0]);


//            //这个步骤很重要，注册一个端口，绑定url后用于客户端通过rmi方式连接JMXConnectorServer
//            LocateRegistry.createRegistry(8999);
//            //URL路径的结尾可以随意指定，但如果需要用Jconsole来进行连接，则必须使用jmxrmi
//            JMXServiceURL url = new JMXServiceURL
//                    ("service:jmx:rmi:///jndi/rmi://localhost:8999/jmxrmi");
//            JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);
//            System.out.println("begin rmi start");
//            jcs.start();
//            System.out.println("rmi start");
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        } catch (NotCompliantMBeanException e) {
            throw new RuntimeException(e);
        } catch (InstanceAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (MBeanRegistrationException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(1000000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
