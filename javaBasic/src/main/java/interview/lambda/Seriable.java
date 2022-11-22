package interview.lambda;

import java.io.Serializable;

/**
 * lambda 序列化:
 * <p>
 * {@link java.lang.invoke.SerializedLambda}
 * <p>
 * Apply<Person, String> apply = (p) -> p.getName();
 * Apply<Person, String> apply2 = Person::getName;
 * <p>
 * 1. 这两种lambda表达式序列化后结果是不同的，Person::getName不会在类内部生成新的lambda$main$4708628b$1 静态实现方法，
 * 而是直接调用 Person::getName内部的实现方法了。
 * <p>
 * lambda的实现中，引导类没有变动，都还是LambdaFactory, 实现方法一个是类内部生成的静态方法，另一个直接是调用对象的方法。
 * <p>
 * 2.本质就是 lambda 表达式是代码在编译器期间在字节码会添加一个 lambda$+所在方法名的方法。方法引用不会生成这个 lambda$方法，而是直接调用引用的方法。
 * 无论是表达式还是方法引用都会在编译期间设置一个引导方法。在运行时，调用字节码 invokedynamic 调用，最终通过 hotspot
 * 一系列方法最终会调到 LambdaMetafactory 这个类中的 metafactory 方法。这个方法会使用被 jdk 修改过的 asm(字节码增强技术)
 * 生成一个内部静态类。而 metafactory 会返回一个 CallSite。
 * 这个 CallSite 中的 methodhandle 就是内部静态类中的你写的 lambda 表达式，最后这个 CallSite 会和编译期间生成的引导方法链接。
 * 然后调用的时候通过引导方法找到 CallSite,再找到内部静态类中的方法，最后指向你代码中写的 lambda 表达式。
 * <p>
 * 如果是方法引用那在 metafactory 中就不会生成静态内部类，而是直接使用那个类，CallSite 就表示你引用的那个方法。
 * 3. 方法引用有
 * 3.1 静态方法引用 （Seriable::toName） ---> 因为新生成的lambda实现就是静态方法，所以就不用生成了
 * 3.2 对象方法引用 (Person::getName)  ---> 调用的是本身对象的内部普通方法， 也无需生成，直接调用对象的方法即可
 * 3.3 实例方法引用 （person::compact） ---> 需要使用外部对象的普通方法，和对象方法调用逻辑一样，这里序列化的时候多了外部对象。
 *
 * @author mahao
 * @date 2022/11/22
 */
public class Seriable {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("zs");
        person.setAge(18);
        //普通函数  -> invokeStatic lambda$main$4708628b$1
        Apply<Person, String> apply = (p) -> p.getName();
        //对象方法引用 -> invokeSpecial 对象:内存地址(lambda传入的对象)  方法名：Person.getName
        Apply<Person, String> apply2 = Person::getName;
        //静态方法引用  ->invokeStatic Seriable.toName
        Apply<Person, String> apply3 = Seriable::toName;
        //实例方法引用  ->invokeSpecial 对象:内存地址(序列化后person对象)  方法名：Person.getName
        Apply<Person, String> apply4 = person::compact;


        SerializedLambda lambda = SerializedLambda.resolve(apply);
        System.out.println("获取lambda方法实现所在的类名:" + lambda.getImplClass());
        System.out.println("获取lambda方法实现所在的类的方法名:" + lambda.getImplMethodName());
        System.out.println("获取lambda方法实现的方法接口:" + lambda.getFunctionalInterfaceClassName());


        SerializedLambda lambda2 = SerializedLambda.resolve(apply2);
        System.out.println("获取lambda方法实现所在的类名:" + lambda2.getImplClass());
        System.out.println("获取lambda方法实现所在的类的方法名:" + lambda2.getImplMethodName());
        System.out.println("获取lambda方法实现的方法接口:" + lambda2.getFunctionalInterfaceClassName());

        SerializedLambda lambda3 = SerializedLambda.resolve(apply3);
        System.out.println("获取lambda方法实现所在的类名:" + lambda3.getImplClass());
        System.out.println("获取lambda方法实现所在的类的方法名:" + lambda3.getImplMethodName());
        System.out.println("获取lambda方法实现的方法接口:" + lambda3.getFunctionalInterfaceClassName());

        //这个时候person也会被序列化的
        SerializedLambda lambda4 = SerializedLambda.resolve(apply4);
        System.out.println("获取lambda方法实现所在的类名:" + lambda4.getImplClass());
        System.out.println("获取lambda方法实现所在的类的方法名:" + lambda4.getImplMethodName());
        System.out.println("获取lambda方法实现的方法接口:" + lambda4.getFunctionalInterfaceClassName());

    }

    static class Person implements Serializable {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String compact(Person p) {
            return p.getName() + this.name;
        }
    }

    public static String toName(Person p) {
        return p.getName();
    }
}
