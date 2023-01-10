package cn.sort.provincesort;

import cn.sort.reversesort.FlowBean;

/**
 * 懒的写属性，继承了，然后重写copareTo方法。
 * 因为需要比较手机号，所以需要添加手机号码作为属性之一。
 *
 * @author: mahao
 * @date: 2020/2/27
 */
public class FlowBean2 extends FlowBean {

    private String number;


    @Override
    public int compareTo(FlowBean o1) {
        if (!(o1 instanceof FlowBean2)) {
            throw new ClassCastException(o1.getClass() + " can not cast " + this.getClass());
        }
        FlowBean2 o = (FlowBean2) o1;

        //先比较号码，号码一致，比较流量。
        int i = number.substring(0, 3).compareTo(o.number.substring(0, 3));
        if (i == 0) {
            return getSumFlow() - o.getSumFlow();
        }
        return i;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
