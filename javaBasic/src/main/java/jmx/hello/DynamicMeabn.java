package jmx.hello;

import org.softee.management.annotation.Description;
import org.softee.management.annotation.MBean;
import org.softee.management.helper.MBeanRegistration;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * DynamicMeabn 注册与实现:
 * 低层级api实现，手动编码实现的注册属性与mbean。
 *
 * @author mash
 * @date 2025/2/7 下午11:28
 */
public class DynamicMeabn {

    public static void main(String[] args) throws Exception {
        ThreadPoolJava threadPool = new ThreadPoolJava();

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName helloName = new ObjectName("jmx:name=dynamicBeanPool");
        mBeanServer.registerMBean(threadPool, helloName);

        Thread.sleep(1000000L);
    }


    //使用原生的API
    public static class ThreadPoolJava implements DynamicMBean {

        private Map<String, Double> metrris = new HashMap<>();

        @Override
        public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
            System.out.println("getAttribute:  " + attribute);

            return "zs1";
        }

        @Override
        public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
            String Name = attribute.getName();
            Object value = attribute.getValue();
            System.out.println("setAttribute: 1 " + Name);
            System.out.println("setAttribute: 2 " + value);
        }

        @Override
        public AttributeList getAttributes(String[] attributes) {
            System.out.println("getAttributes:" + attributes);
            AttributeList objects = new AttributeList();
            objects.add(new Attribute("name", "sdd"));
            return objects;
        }

        @Override
        public AttributeList setAttributes(AttributeList attributes) {
            System.out.println("setAttributes:" + attributes);
            return null;
        }

        @Override
        public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
            System.out.println(actionName);
            return "ssss";
        }

        @Override
        public MBeanInfo getMBeanInfo() {
            System.out.println("getMBeanInfo ");
            try {
                MBeanConstructorInfo constructors = new MBeanConstructorInfo(" Constructs a HelloDynhhhamic object", this.getClass().getConstructors()[0]);
                MBeanAttributeInfo attributes = new MBeanAttributeInfo("Name3", "java.lang.String", "Name: name string.", true, true, false);
                MBeanOperationInfo operations = new MBeanOperationInfo("print2", "print(): print the name", null, "void", MBeanOperationInfo.INFO);

                return new MBeanInfo(this.getClass().getName(), "哈哈哈哈描述", new MBeanAttributeInfo[]{attributes}, new MBeanConstructorInfo[]{constructors}, new MBeanOperationInfo[]{operations}, new MBeanNotificationInfo[0]);
            } catch (Exception r) {
                throw new RuntimeException(r);
            }
        }
    }


    @MBean
    @Description("MBeanThreadPool")
    public static class ThreadPool extends ThreadPoolExecutor {

        private MBeanRegistration registration;

        public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>());
            try {
                this.registration = new MBeanRegistration(this, new ObjectName("com.mh:type=Pool,name=pool1"));
                this.registration.register();
            } catch (Exception var11) {
                var11.printStackTrace();

            }
        }

    }
}
