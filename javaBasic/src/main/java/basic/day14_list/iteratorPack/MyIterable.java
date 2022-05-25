package basic.day14_list.iteratorPack;

/**
 * 自定义获取迭代器接口
 *
 * @author  mahao
 * @date:  2019年3月2日 下午3:30:03
 */
public interface MyIterable<T> {
	
	MyIterator<T> getIterator();
}
