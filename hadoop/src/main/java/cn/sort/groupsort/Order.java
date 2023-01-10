package cn.sort.groupsort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 将订单记录封装成类
 *
 * @author: mahao
 * @date: 2020/2/27
 */
public class Order implements WritableComparable<Order> {

    private int oid;
    private String pid;
    private double price;


    //按照订单排序，订单号一致了，按照商品的价格逆序输出。
    @Override
    public int compareTo(Order o) {
        int i = oid - o.oid;
        if (i == 0) {
            return price > o.price ? -1 : price == o.price ? 0 : 1;
        }
        return i;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(oid);
        out.writeUTF(pid);
        out.writeDouble(price);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        oid = in.readInt();
        pid = in.readUTF();
        price = in.readDouble();
    }


    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return oid + "\t" + pid + "\t" + price;
    }
}
