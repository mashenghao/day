package basic.day14_map.hashMapDemo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/**
 * map练习
 * 
 * 向map集合中存入学生，
 * 学生属性，姓名，年龄，姓名年龄相同为同一个人
 * 保证学生的唯一性；
 *
 * @author  mahao
 * @date:  2019年3月5日 下午7:36:00
 */
public class MapTest {
	
	public static void main(String[] args) {
		Set<Student> set = new HashSet<Student>();
		set.add(new Student("001",15));
		set.add(new Student("002",17));
		set.add(new Student("001",13));
		set.add(new Student("002",17));
		System.out.println(set);
		
		/*
		 * 向map集合中存入学生，
		 * 学生属性，姓名，年龄，姓名年龄相同为同一个人
		 * 保证学生的唯一性；
		 */
		HashMap<Student,Integer> map = new HashMap<Student,Integer>();
		map.put(new Student("001",15) , 1);
		map.put(new Student("002",17) , 1);
		map.put(new Student("001",13) , 1);
		map.put(new Student("002",17) , 1);
		System.out.println(map);
		
		/*
		 * 向map集合中存入学生，
		 * 学生属性，姓名，年龄，姓名年龄相同为同一个人
		 * 保证学生的唯一性；
		 * 让数据按照姓名降序排序；
		 */
		TreeMap<Student, Integer> map2 = new TreeMap<Student,Integer>();
		map2.put(new Student("001",15) , 1);
		map2.put(new Student("002",17) , 2);
		map2.put(new Student("001",13) , 3);
		map2.put(new Student("002",17) , 4);
		System.out.println(map2);
		
		/*
		 * 字符串可以实现了comparable接口，所以在操作的时候
		 * 可以很方便是使用字符串当key值，在hashMap中，字符串也
		 * 重写了hashcode和equals方法，所以也可以进行hashMap的
		 * key值；
		 */
		TreeMap<String, Integer> map3 = new TreeMap<String,Integer>();
		map3.put("a", 1);
		map3.put("d", 1);
		map3.put("ab", 1);
		map3.put("a", 1);
		System.out.println(map3);
	}
}

//HashSet实现元素唯一性
class Student extends Person {

public Student(String name, int age) {
super(name, age);
}

@Override
public int hashCode() {
return getName().hashCode()+getAge()*10;
}

public boolean equals(Object obj) {
if (!(obj instanceof Student))
throw new ClassCastException("格式转换异常");
Student stu = (Student) obj;
System.out.println(obj);
if (getAge() - stu.getAge() == 0) {
return getName().equals(stu.getName());
}
return false;
}
}
						//TreeSet实现元素唯一性
class Person implements Comparable<Person>{
	
	private String name;
	private int age;
	
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	
	@Override
	public int compareTo(Person o) {
		int num = getAge()< o.getAge() ? -1 :(getAge() == o.getAge() ? 0 : 1);
		System.out.println(this.name + "   "+o.name);
		if(num==0)
			return getName().compareTo(o.getName());
		return num;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
}

