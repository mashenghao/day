package basic.day25;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

import basic.day19_IO.User;

/**
 * 使用反射操作泛型
 * @author L J
 */
@SuppressWarnings("all")
public class ReflectionDemo3 {
    public static void main(String[] args) {
        try {
            // 获得指定方法的参数泛型信息
            Method m = ReflectionDemo3.class.getMethod("test01", Map.class,
                    List.class);
            Type[] t = m.getGenericParameterTypes();
            for (Type paramType : t) {
                System.out.println(paramType);
                if (paramType instanceof ParameterizedType) {
                    Type[] genericTypes = ((ParameterizedType) paramType)
                            .getActualTypeArguments();
                    for (Type genericType : genericTypes) {
                        System.out.println("泛型类型：" + genericType);
                    }
                }
            }

            // 获得指定方法返回值泛型类型
            Method m2 = ReflectionDemo3.class.getMethod("test02", null);
            Type returnType = m2.getGenericReturnType();
            System.out.println(returnType);
            if (returnType instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) returnType)
                        .getActualTypeArguments();
                for (Type genericType : genericTypes) {
                    System.out.println("泛型类型：" + genericType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test01(Map<String, User> map, List<User> list) {
        System.out.println("ReflectionDemo3.test01()");
    }

    public Map<Integer, User> test02() {
        System.out.println("ReflectionDemo3.test02()");
        return null;
    }
}