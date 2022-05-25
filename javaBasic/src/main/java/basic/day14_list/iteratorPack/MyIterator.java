package basic.day14_list.iteratorPack;

/**
 * 自定义迭代器接口
 *
 * @author  mahao
 * @date:  2019年3月2日 下午3:24:41
 */
public interface MyIterator<E> {
	
	public boolean hasNext();
	
	public E next();
	
	public E remove();
}
