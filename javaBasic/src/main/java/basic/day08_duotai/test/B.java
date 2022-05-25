package basic.day08_duotai.test;

public class B extends A{
	
    public String show(B obj){
        return ("B and B");
    }
    
    @Override
    public String show(A obj){
        return ("B and A");
    } 
}