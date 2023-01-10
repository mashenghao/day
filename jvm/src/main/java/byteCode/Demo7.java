package byteCode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * java动态代理运行机制解析:
 *
 * jdk的动态代理是根据传入的被代理类的父接口，创建一个代理的实现类，
 * 在实现类中，将里面的方法都进行了实现，里面的实现逻辑是都是一样的，都是调用
 * InvocationHandler的invoke方法，这个InvocationHandler对象是我们自定义创建的，这就是
 * 为什么每个代理方法都是执行invoke的原因，具体代码是
 *         super.h.invoke(this, m4, (Object[])null);
 *  h是InvocationHandler的对象，生成的代理类中的每个方法，都调用这个逻辑，方法的参数是，代理对象，
 *  方法本身Method，还有调用的参数，具体的实现逻辑就存在invocate方法。对于父类的方法，代理类只是覆盖了
 *  以下几个：
 *          m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
 *          m2 = Class.forName("java.lang.Object").getMethod("toString");
 *          m0 = Class.forName("java.lang.Object").getMethod("hashCode");
 *
 *
 *
 */
public class Demo7 {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Subject realSubject = new RealSubject();
        Subject subject = (Subject) Proxy
                .newProxyInstance(realSubject.getClass().getClassLoader(),
                        realSubject.getClass().getInterfaces(), new MyHandler(realSubject));
        subject.doSomething();
        System.out.println(subject.getClass());
        System.out.println(subject.getClass().getSuperclass());

    }

}


interface Subject {

    void doSomething();
    void doSomething2();
}

class RealSubject implements Subject {

    @Override
    public void doSomething() {
        System.out.println(RealSubject.class.getName());
    }

    @Override
    public void doSomething2() {
        System.out.println("doSomething2");
    }
}

class MyHandler implements InvocationHandler {

    Subject target;

    public MyHandler(Subject target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = null;
        System.out.println("before  " + method.getName());
        obj = method.invoke(target, args);
        System.out.println("after ...");
        return obj;
    }
}