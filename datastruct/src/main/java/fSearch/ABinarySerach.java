package fSearch;

/**
 * 二分查找
 *
 * 从一组有序数组中，查找到想要的数据
 *
 * @author mahao
 * @date 2019年4月18日 下午9:25:22
 */
public class ABinarySerach {

	public static void main(String[] args) {
		int arr[] = { 1, 3, 5, 5, 6, 7, 9, 78, 523, 561, 5656, 9563, 26500, 98884 };
		int index = binarySearch(arr, 98884);
		System.out.println(index + "---");
	}

	// 二分查找
	public static int binarySearch(int[] arr, int a) {

		return search2(arr, 0, arr.length, a);
	}

	public static int search2(int[] arr, int begin, int end, int a) {
		while (begin <= end ) {
			int mid = (begin + end) >> 1;
			if (arr[mid] > a) {
				end = mid - 1;
			} else if (arr[mid] < a) {
				begin = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	public static int search(int[] arr, int begin, int end, int a) {
		int mid = (begin + end) >> 1;
		if (begin > end)
			return -1;
		if (arr[mid] > a) {
			return search(arr, begin, mid - 1, a);
		} else if (arr[mid] < a) {
			return search(arr, mid + 1, end, a);
		} else {
			return mid;
		}
	}
}
