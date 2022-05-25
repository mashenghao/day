package interview;


/**
 * super关键字的使用
 * <p>
 * super是调用父类的方法或属性。
 * <p>
 * https://blog.csdn.net/mahao25/article/details/87989031
 *
 * @author: mahao
 * @date: 2019/8/22
 */
public class DSuper {

    public static void main(String[] args) {
        Parent p = new Parent("Parent");
        Parent cp = new Child("cp");
        Child c = new Child("Child");
        /*
           以下结果的输出，会根据引用指向的实际类型进行方法的调用，这个体现的是
           方法的重写，是java多态性的体现。
         */
        p.function();
        cp.function();
        c.function();

        /*
        对于对象的属性而言，属性的数值，是静态赋值，是根据引用类型来确定值，就像方法的重载，去根据参数的静态类型而不是参数
        引用指向的实际对象类型去确定参数的，所以，这个name是属性，数值是根据左面引用类型确定数值的。
         */
        System.out.println(p.name);//Parent

        System.out.println(cp.name);//parent

        Child c2 = (Child) cp;
        System.out.println(c2.name);//cp

        System.out.println(c.name);//Child
    }

}

class Parent {

    String name = "parent";

    public Parent() {
    }

    public Parent(String name) {
        this.name = name;
    }

    public void function() {
        System.out.println("parent method invoke ... ");
    }
}

class Child extends Parent {

    String name = "child";

    public Child(String name) {
        this.name = name;
    }

    public void function() {
        System.out.println("Child method invoke ... ");
    }
}