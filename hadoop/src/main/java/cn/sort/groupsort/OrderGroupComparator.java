package cn.sort.groupsort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 继承WritableComparator，重写方法，添加新的比较规则。
 *
 * @Date: 2020/2/27 12:53
 * @Author: mahao
 * @Description:
 */
public class OrderGroupComparator extends WritableComparator {

    //要求如此，我也不知道为啥。
    protected OrderGroupComparator() {
        super(Order.class, true);
    }

    //重写比较规则，让一部分值相同比较值相同，即可。
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Order o1 = (Order) a;
        Order o2 = (Order) b;

        //之前的比较规则是oid和price，现在只要是oid相同，就是一个分组。
        return o1.getOid() - o2.getOid();

        //向按照商品的id分组呢? 组成的数据不会按照pid分组，比较的逻辑应该是， 在shuffle根据
        //key进行排序后，使用传入的Comparator接口，进行比较元素，当比较值返回0，暂存，继续比较下个元素，
        //当返回不为0时，结束比较，定义为一个分组。是这种比较规则，所以规则符合最左匹配原则。
        // return o1.getPid().compareTo(o2.getPid());

    }

    /*
    0000001	Pdt_01	222.8
    0000002	Pdt_05	722.4
    0000001	Pdt_02	33.8
    0000003	Pdt_06	232.8
    0000003	Pdt_02	33.8
    0000002	Pdt_03	522.8
    0000002	Pdt_04	122.4

    0000001	Pdt_01	222.8
    0000001	Pdt_02	33.8
    0000002	Pdt_05	722.4
    0000002	Pdt_03	522.8
    0000002	Pdt_04	122.4
    0000003	Pdt_06	232.8
    0000003	Pdt_02	33.8
     */
}
