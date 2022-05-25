package search;

/**
 * @program: algorithm
 * @Date: 2019/6/5 18:47
 * @Author: mahao
 * @Description: 二分查找的变形与使用
 * 二分查找的变种:
 * 2.1 查找第一个与key相等的元素
 * 2.2 查找最后一个与key相等的元素
 * 2.3 查找最后一个等于或者小于key的元素
 * 2.4 查找最后一个小于key的元素
 * 2.5 查找第一个等于或者大于key的元素
 * 2.6 查找第一个大于key的元素
 *
 * https://www.cnblogs.com/luoxn28/p/5767571.html
 */
public class BinarySerach {

    public static void main(String[] args) {
        BinarySerach binarySerach = new BinarySerach();

        int index = binarySerach.search6(4, new int[]{1, 2, 3, 4, 4, 5, 7, 9});
        System.out.println(index);
    }

    /**
     * 标准的二分搜索，查找是否相同元素，返回下标
     *
     * @param key
     * @param arr
     * @return
     */
    public int search(int key, int[] arr) {

        int left = 0;//定义搜索的左右边界
        int right = arr.length - 1;

        while (left <= right) {//一定要是<=作为结束条件，当只剩下一下元素时，左右元素时下标相同
            int mid = (left + right) >> 1;//二分搜索的关键，通过取中间元素
            if (arr[mid] == key) {
                return mid;//如果元素相等，则返回下标
            } else if (arr[mid] > key) {
                right = mid - 1;//如果mid大于key值，则要查找的元素在左半部分
            } else {
                left = mid + 1;//如果mid小于key的值，则要找的元素在右半部分
            }
        }
        return -1;//查询不到，返回-1
    }

    /**
     * 1 查找第一个与key相等的元素
     * 这说明元素可能重复，怎么能让查出来的是首个元素？
     * 思路：
     * 1.可以做个暂存值，当数据和这个值一样时候，存下下标，然后比较下一个
     * 2.可以用二分查找的变种的实现: 如果right查找到了，不进行返回，继续查找下一个，
     * 直到让right的值，小于key，right则会等待。
     *
     * @param key
     * @param array
     * @return
     */
    public int search1(int key, int[] array) {
        int left = 0, mid = -1;
        int right = array.length - 1;
        while (left <= right) {//二分查找的结束条件不变，还是左超过右，为结束条件
            mid = (left + right) >> 1;
            /*
               这个条件是查找首个出现的元素的关键，array[mid]>=key表示，当array的值大于key值时，
               right替换为mid-1，这个是可以理解的，当array[mid]和key的值相等时，right的值依然是
               向右移动的，但是这个right的移动的位置最小是，key值的首个元素的前一个位置。因为，
               array[mid]>=key,当把所有与key的值跳过后，right就不会再向前移动了，因为前面已经没有数据
               在满足大于等于key的数据了，所以right的值会一直不变，等待着left逐渐变大，直到left>right,
               这时候left=right+1，而right停的位置，就是key的前一个位置。
             */
            if (array[mid] >= key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
            if (left < array.length && array[left] == key)//如果key值大于最大数，则会越界；key属于中间值不存在，则第二个不成立
                return left;
        }
        return -1;
    }

    /**
     * 2.2 查找最后一个与key相等的元素
     * 思路就是：让要等待的哪一方，停止在要查找的那个key值的前一个位置。
     *
     * @param key
     * @param array
     * @return
     */
    public int search2(int key, int[] array) {
        int left = 0;
        int right = array.length - 1;
        int mid = 0;
        while (left <= right) {//循环条件不改变
            mid = (left + right) / 2;
            if (array[mid] <= key) {//类似查找首次出现的元素，这次让left停止改变，等待right到达left的前一个元素为结束条件，这前一个元素就是key值。
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (right >= 0 && array[right] == key) {
            return right;
        }
        return -1;
    }

    /**
     * 2.3 查找最后一个等于或者小于key的元素
     * 思路：这句话是说，等于key的值有很多个，返回这些值里面最右边的哪一个，如果不存在相等，则
     * 返回最右面的小于key的那个。
     * <p>
     * 类似于查找最后一个等于key值的元素，让left停止在key的后一个元素，让right去查找元素
     *
     * @param key
     * @param array
     * @return
     */
    public int search3(int key, int[] array) {
        int left = 0;
        int right = array.length - 1;
        int mid = 0;
        while (left <= right) {//循环条件不改变
            mid = (left + right) / 2;
            if (array[mid] <= key) {
                left = mid + 1; //如果元素存在，left停留的位置就是最后一个元素的下一个位置，不存在，就是大于key的第一个元素
            } else {
                right = mid - 1;
            }
        }
        return right;//right这个时候不需要判断，元素不存在，即都大于key值，返回的是-1 ，否则及时返回的苏需要的index
    }

    /**
     * 2.4 查找最后一个小于key的元素
     * 思路：
     * 查找最后一个小于key的元素，就是查找第一个key的值的算法，因为查找第一个key中的算法中，
     * right访问的就是最后一个小于key的元素。
     *
     * @param key
     * @param array
     * @return
     */
    public int search4(int key, int[] array) {
        int left = 0;
        int right = array.length - 1;
        int mid = 0;
        while (left <= right) {//循环条件不改变
            mid = (left + right) / 2;
            if (array[mid] >= key) {//查找的是第一个key出现的位置的前一个,则让right等待
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    /**
     * 2.5 查找第一个等于或者大于key的元素
     * 思路:
     * 查找第一个等于或者大于key的元素，也就是说等于查找key值的元素有好多个，
     * 返回这些元素最左边的元素下标；如果没有等于key值的元素，
     * 则返回大于key的最左边元素下标。
     *
     * @param key
     * @param array
     * @return
     */
    public int search5(int key, int[] array) {
        int left = 0;
        int right = array.length - 1;
        int mid = 0;
        while (left <= right) {//循环条件不改变
            mid = (left + right) / 2;
            if (array[mid] >= key) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    /**
     * 2.6 查找第一个大于key的元素
     *
     * 第一个大于key的值，就是查找最后一个key值的元素，然后返回left
     *
     * @param key
     * @param array
     * @return
     */
    public int search6(int key, int[] array) {
        int left = 0;
        int right = array.length - 1;
        int mid = 0;
        while (left <= right) {//循环条件不改变
            mid = (left + right) / 2;
            if (array[mid] <= key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    public int forSearch(int key, int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++)
            if (arr[i] == key)
                return i;
        return -1;
    }

}

