package interview;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * hashCode 和 equals 方法
 *
 * @author: mahao
 * @date: 2019/8/22
 */
public class EHashCodeAndEquals {

    public static void main(String[] args) {
        Set<Person> set = new HashSet<>();
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        Person p4 = new Person();
        set.add(p1);
        set.add(p2);
        set.add(p2);
        set.add(p3);
        set.add(p4);
        System.out.println(set.size());
    }
}

class Person {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    /*@Override
    public int hashCode() {
        int result = name == null ? 0 : name.hashCode();
        return result * 31 + age;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true;

        if (obj == null || obj.getClass() != getClass()) return false;

        Person p = (Person) obj;
        if (age != p.getAge()) return false;
        return name != null ? name.equals(p.name) : p.name == null;
    }
*/
    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        return name != null ? name.equals(person.name) : person.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }*/
}