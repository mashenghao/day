package interview;

import java.io.Serializable;

/**
 * 重写方法返回值可以是子类。 参数不能变动。
 *
 * @author mahao
 * @date 2022/12/07
 */
public class 重写方法返回值为子类 {

    interface Parent {

        Serializable toChar(Serializable sre);
    }

    interface Son extends Parent {


        @Override
        Character toChar(Serializable ss);
    }
}
