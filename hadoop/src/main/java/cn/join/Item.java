package cn.join;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author: mahao
 * @date: 2020/2/29
 */
public class Item implements Writable {

    private String oid = "";//订单id
    private String pid = "";//商品id
    private int amount; //数量
    private String pname = ""; //商品名称
    private String flag = ""; //表的标记


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(oid);
        out.writeUTF(pid);
        out.writeInt(amount);
        out.writeUTF(pname);
        out.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        oid = in.readUTF();
        pid = in.readUTF();
        amount = in.readInt();
        pname = in.readUTF();
        flag = in.readUTF();
    }

    @Override
    public String toString() {
        return oid + "\t"
                + pid + "\t"
                + pname + "\t"
                + amount + "\t"
                + flag;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
