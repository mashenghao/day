package stack;

/**
 * 栈
 * <p>
 * 先进后出
 */
public class MainClass {

    int elementCount = 0;//top 栈顶指针

    int[] elementData = new int[10];

    public int size(){
        return elementCount ;
    }

    public void push(int ele){
        ensureCapatity(elementCount + 1 );
        elementData[elementCount++] = ele;
    }

    private void ensureCapatity(int minCapatity) {
        if(minCapatity > elementData.length){

        }
    }


}
