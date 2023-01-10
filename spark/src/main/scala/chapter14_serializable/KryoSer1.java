package chapter14_serializable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;

/**
 * kryo类型的序列化
 *
 * @author: mahao
 * @date: 2021/12/11
 */
public class KryoSer1 {

    public static void main(String[] args) {
        Kryo kryo = new Kryo();

        kryo.register(JavaSer1.User.class, new JavaSerializer());
        kryo.register(JavaSer1.User.class, new KryoSerializer());
        JavaSer1.User user = new JavaSer1.User("zs", 19);
        Output output = new Output(300, 1024);
        kryo.writeClassAndObject(output, user);

        Object o = kryo.readClassAndObject(new Input(output.toBytes()));
        System.out.println(o);
    }

    public static class KryoSerializer extends Serializer {

        @Override
        public void write(Kryo kryo, Output output, Object object) {
            if (object instanceof JavaSer1.User) {
                JavaSer1.User user = (JavaSer1.User) object;
                //对于基本数据类型，这两种写法一致，因为默认的序列化类也是直接写数值的。
                output.writeString(user.getName());
                output.writeInt(user.getAge());

                kryo.writeObject(output, user.getName());
                kryo.writeObject(output, user.getAge());
                System.out.println("kryo写出数据");
            }
        }

        @Override
        public Object read(Kryo kryo, Input input, Class type) {
            if (type == JavaSer1.User.class) {
                System.out.println("kryo读取数据");
                //这里的kryo对象，使用来对象的属性也是对象。
                new JavaSer1.User(input.readString(), input.readInt());
                return new JavaSer1.User(kryo.readObject(input, String.class), kryo.readObject(input, Integer.class));
            }
            return null;
        }
    }
}
