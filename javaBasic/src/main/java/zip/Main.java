package zip;

import org.junit.rules.Stopwatch;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

/**
 * @author mash
 * @date 2023/10/17 22:08
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String str = "{\"properties\":{\"maskColor\":{\"r\":0,\"g\":0,\"b\":0,\"a\":0.6},\"maskCloseEnabled\":true,\"maskActionId\":\"action-mask-click\",\"maskElementId\":\"e9b08b17-0c5a-470f-a107-ac79af37d6cb\"},\"template\":{\"type\":\"column\",\"layout\":{\"align\":\"center\",\"width\":\"310px\",\"margin\":{\"top\":\"60px\"}},\"subviews\":[{\"type\":\"image_button\",\"properties\":{\"name\":\"关闭 icon\",\"image\":\"https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg?x=/2023/9/17/10/16975088054270105.txt/xxx_close_icon.png\",\"elementType\":\"close\",\"elementId\":\"e9b08b17-0c5a-470f-a107-ac79af37d6cb\",\"elementName\":\"top_right_close\"},\"layout\":{\"width\":\"30px\",\"height\":\"30px\",\"align\":\"right\"},\"action\":{\"IOS\":{\"actionType\":\"sfbfshbfjfg\",\"intent\":\"\",\"url\":\"\",\"closePopup\":true},\"ANDROID\":{\"actionType\":\"fqng[gb\",\"intent\":\"\",\"url\":\"\",\"closePopup\":true}}},{\"type\":\"column\",\"properties\":{\"backgroundColor\":{\"r\":255,\"g\":255,\"b\":255,\"a\":1},\"cornerRadius\":\"8px\"},\"layout\":{\"align\":\"center\",\"width\":\"310px\",\"margin\":{\"top\":\"10px\"},\"padding\":{\"bottom\":\"17px\"}},\"subviews\":[{\"type\":\"image\",\"properties\":{\"name\":\"图片\",\"image\":\"https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg?x=/2023/9/17/10/16975088054270105.txt/sftest_4e5793402e3e5ba93b2a0fa8fc333646578bc3e8.png\"},\"layout\":{\"align\":\"center\",\"width\":\"310px\",\"height\":\"280px\"}},{\"type\":\"column\",\"layout\":{\"width\":\"290px\",\"align\":\"center\",\"margin\":{\"top\":\"16px\"}},\"subviews\":[{\"type\":\"button\",\"properties\":{\"name\":\"按钮 1\",\"text\":\"\",\"font\":\"18px\",\"color\":{\"r\":255,\"g\":255,\"b\":255,\"a\":1},\"backgroundColor\":{\"r\":255,\"g\":90,\"b\":90,\"a\":1},\"borderWidth\":\"2px\",\"borderColor\":{\"r\":255,\"g\":90,\"b\":90,\"a\":1},\"cornerRadius\":\"20px\",\"elementId\":\"e9b08b17-0c5a-470f-a107-ac79af37d6cb\"},\"layout\":{\"width\":\"290px\",\"height\":\"40px\"},\"action\":{\"IOS\":{\"actionType\":\"fnjfv\",\"intent\":\"intent://feqnwjnj.fjbj2.efnojnf/sssddd?#Intent;scheme=ewjnfoj2j;launchFlags=0x04000000;package=dvjnwjv;component=wrnw;S.gttask=;S.fekjfn=%E3%80%81%E6%80%BB%E9%95%BF%E5%BA%A6%E5%B0%8F%E4%BA%8E1000%E5%AD%97%E8%8A%82%EF%BC%8C%E9%80%9A%E7%9F%A5%E5%B8%A6intent%E4%BC%A0%E9%80%92%E5%8F%82%E6%95%B0%EF%BC%88%E4%BB%A5intent%3A%E5%BC%80%E5%A4%B4%EF%BC%8C%3Bend%E7%BB%93%E5%B0%BE%EF%BC%89%E3%80%82%E5%85%B6%E4%B8%AD%22intent%22%E5%92%8C%E2%80%9C%3Bend%22%E6%9C%AC%E5%B7%A5%E5%85%B7%E4%BC%9A%E8%87%AA%E5%8A%A8%E4%B8%BA%E6%82%A8%E8%BF%BD%E5%8A%A0%E3%80%82;S.aw=%E7%AE%80%E5%8D%95%E7%A4%BA%E4%BE%8B%EF%BC%9Aintent%3A%E5%8D%8F%E8%AE%AE%E5%9C%B0%E5%9D%80%23Intent%3Bscheme%3D%E5%8D%8F%E8%AE%AE%E5%A4%B4%3Bcomponent%3D%E4%BD%A0%E7%9A%84%E5%8C%85%E5%90%8D%2F%E4%BD%A0%E8%A6%81%E6%ef ejfnhjebfhbfhebfhbehfb2u3hu3bruueuoiuqireuoirqww89%93%E5%BC%80%E7%9A%84activity%E5%85%A8%E5%BE%84%3BS.parm1%3Dvalue1%3BS.parm2%3Dv;end\",\"url\":\"https://cn.bing.com/search?q=%E9%95%BF%E6%96%87%E6%9C%AC&qs=n&form=QBRE&sp=-1&lq=0&pq=chang%27wen%27ben&sc=2-13&sk=&cvid=4A3DD1F45C60410CBE4F5315EE75451D&ghsh=0&ghacc=0&ghpl=\",\"closePopup\":false},\"ANDROID\":{\"actionType\":\"wrij43ouib2f\",\"intent\":\"intent://feqnwjnj.fjbj2.efnojnf/sssddd?#Intent;scheme=ewjnfoj2j;launchFlags=0x04000000;package=dvjnwjv;component=wrnw;S.gttask=;S.dcff=%E5%85%B6%E5%AE%83%E5%9D%87%E5%B0%8F%E5%86%99%E5%8D%B3%E5%8F%AF%E3%80%82%E9%83%A8%E5%88%86%E5%8E%82%E5%95%86%E4%BB%85%E6%94%AF%E6%8C%81String%E7%B1%BB%E5%9E%8B%EF%BC%8C%E5%BB%BA%E8%AE%AE%E4%BD%BF%E7%94%A8String%E7%B1%BB%E5%9E%8B%E3%80%82%204%E3%80%81%E8%8B%A5%E8%87%AA%E5%AE%9A%E4%B9%89%E5%8F%82%E6%95%B0%E4%B8%AD%E5%8C%85%E5%90%AB%E7%89%B9%E6%AE%8A%E5%AD%97%E7%AC%A6%EF%BC%8C%E7%82%B9%E5%87%BB%E7%A1%AE%E8%AE%A4%E5%90%8E%E6%9C%AC%E5%B7%A5%E5%85%B7%E5%B0%86%E4%BC%9A%E4%B8%BA%E6%82%A8%E8%87%AA%E5%8A%A8UrlEnCode%E7%BC%96%E7%A0%81%E3%80%82%205%E3%80%81%E9%80%9A%E8%BF%87%E6%9C%AC%E5%B7%A5%E5%85%B7%E5%A1%AB%E5%86%99%E7%9A%84%E7%9B%B8%E5%85%B3%E5%86%85%E5%AE%B9%EF%BC%8C%E7%82%B9%E5%87%BB%E7%A1%AE%E8%AE%A4%E5%90%8E%E5%B0%86%E4%BC%9A%E8%A6%86%E7%9B%96%E6%82%A8%E6%9C%AC%E8%BA%AB%E5%B7%B2%E7%BB%8F%E5%A1%AB%E5%86%99%E7%9A%84intent%E5%86%85%E5%AE%B9%E3%80%82;end\",\"url\":\"https://cn.bing.com/search?q=%E5%8E%8B%E7%BC%A9%E5%AD%97%E8%8A%82%E6%95%B0%E7%BB%84%E8%BD%AC%E5%AD%97%E7%AC%A6&qs=n&form=QBRE&sp=-1&lq=0&pq=ya%27suo%E5%AD%97%E8%8A%82%E6%95%B0%E7%BB%84%E8%BD%AC%E5%AD%97%E7%AC%A6&sc=8-13&sk=&cvid=988480ED9D894FBD90E5A26C6D065327&ghsh=0&ghacc=0&ghpl=\",\"closePopup\":false}}}]}]},{\"type\":\"image_button\",\"properties\":{\"name\":\"关闭 icon\",\"image\":\"https://sf-static.sensorsdata.cn/product%2Fclose_icon.png\",\"isHidden\":true,\"localImageName\":\"close\",\"msgType\":\"close\",\"elementId\":\"e9b08b17-0c5a-470f-a107-ac79af37d6cb\",\"elementName\":\"bottom_close\"},\"layout\":{\"width\":\"30px\",\"height\":\"30px\",\"align\":\"center\",\"margin\":{\"top\":\"10px\"}},\"action\":{\"IOS\":{\"actionType\":\"\",\"intent\":\"从 语料角度 处理 思路 很简单，就是 采用某些手段 来 修改原始的输入序列，让他的长度 尽可能小于 512 的最大长度，使其满足 Bert 的 输入范式。常用的方法：Clipping（截断法）：将序列长度超过512的部分直接去掉保留前512个Token或者后512个Token；Pooling（池化法）：截断法最大的问题在于需要丢掉一部分文本信息，如果我们能够保留文本中的所有信息，想办法让模型能够接收文本中的全部信息，这样就能避免文本丢失带来的影响；划窗法：主要见于诸阅读理解任务（如Stanford的SQuAD)。Sliding Window即把文档分成有重叠的若干段，然后每一段都当作独立的文档送入BERT进行处理。最后再对于这些独立文档得到的结果进行整合；压缩法；RNN（循环法）：BERT之所以会有最大长度的限制，是因为其在进行MLM预训练的时候就规定了最大的输入长度，而对于类RNN的网络来讲则不会有句子长度的限制（有多少个token就过多少次NN就行了）。但RNN相较于 Transformer 来讲最大问题就在于效果不好；\",\"url\":\"\",\"closePopup\":true},\"ANDROID\":{\"actionType\":\"\",\"intent\":\"\",\"url\":\"\",\"closePopup\":true}}}]}}";
        for (int i = 0; i < 10; i++) {
            long t1 = System.currentTimeMillis();
            int l1 = gzip(str).length();
            long t2 = System.currentTimeMillis();
            System.out.println("gzip :" + l1 + "  " + (t2 - t1));

            t1 = System.currentTimeMillis();
            l1 = xz(str).length();
            t2 = System.currentTimeMillis();
            System.out.println("xz :" + l1 + "  " + (t2 - t1));


            t1 = System.currentTimeMillis();
            l1 = bzip2(str).length();
            t2 = System.currentTimeMillis();
            System.out.println("xz :" + l1 + "  " + (t2 - t1));
        }

    }

    public static String gzip(String str) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gos = new GZIPOutputStream(outputStream);
        gos.write(str.getBytes());
        gos.flush();
        gos.finish();
        byte[] byteArray = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static String xz(String str) throws Exception {
        return XZ.compress(str);
    }

    public static String bzip2(String str) throws Exception {
        return Bzip2.compress(str);
    }


}
