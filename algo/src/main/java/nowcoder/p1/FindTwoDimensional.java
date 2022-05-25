package nowcoder.p1;

/**
 * 二维数组中的查找
 * <p>
 * 描述：
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
 * 判断数组中是否含有该整数。
 * <p>
 * 思路：
 * 从上到下查找每一行，如果每行的首元素，大于target，则结束，
 * 在每行里面进行二分查找，
 */
public class FindTwoDimensional {

    static int arrays[][] = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};

    public static void main(String[] args) {
        System.out.println(new FindTwoDimensional_True().Find(7, arrays));
    }

    public boolean Find(int target, int[][] array) {

        if (array == null || array.length == 0 || array[0].length == 0)
            return false;
        int columnIndex = 0;
        int length = array.length;
        int begin, end, mid, num;
        while (columnIndex < length && array[columnIndex][0] <= target) {
            System.out.println(columnIndex);
            begin = 0;
            end = array[columnIndex].length - 1;
            while (begin <= end) {
                mid = (begin + end) >> 1;
                num = array[columnIndex][mid];

                if (num > target) {
                    end = mid - 1;
                } else if (num < target) {
                    begin = mid + 1;
                } else {
                    return true;
                }
            }
            columnIndex++;
        }
        return false;
    }

}

/**
 * 二维数组中的查找
 * <p>
 * 描述：
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
 * 判断数组中是否含有该整数。
 * 思路：
 * 仔细读题，在二维数组中，每一行是递增的，每一列也是递增的，是每一列，对二维数组每一列
 * 都是成立的，所以这是个有序矩阵，则只需要从矩阵的一个角左下角或者右上角，必须是元素最大和最小的组合，
 * （右上角），如果目标值大于当前元素，则左移当前元素，若小于当前元素，则下移当前元素，这样移动的值，都在
 * 目标元素的比较范围中，不会丢失比较元素。结束判断，通过移动位置，如果移动越界，则不存在。
 */
class FindTwoDimensional_True {
    public boolean Find(int target, int[][] array) {
        int x=0, y;
        if (array == null || array.length == 0 || array[0].length == 0)
            return false;

        y = array[0].length - 1;//x，y表示当前元素坐标值，先定位到左上角,x表示行，y表示列

        while (array[x][y] != target) {

            if (array[x][y] > target) {//目标元素小于于当前元素，左移
                y--;
            } else { //大于当前元素 ，下移
                x++;
            }
            if (y < 0 || x >= array.length) {
                return false;
            }
        }
        return true;
    }
}
