package syntax.genericParadigm;

import java.util.ArrayList;



//java泛型（二）、泛型的内部原理：类型擦除以及类型擦除带来的问题
//http://blog.csdn.net/lonelyroamer/article/details/7868820

public class TypeErase {
	//测试泛型的class类型
	public void test(){
		ArrayList<String> arrayList=new ArrayList<>();
		arrayList.add("abc");
		ArrayList<Integer> arrayList2=new ArrayList<>();
		arrayList2.add(123);
		System.out.println(arrayList.getClass()==arrayList2.getClass());
	}
	
	//通过反射可以让Integer类执行插入字符串
	public void test1(){
		ArrayList<Integer> arrayList3=new ArrayList<>();
		arrayList3.add(1); //这样调用add方法只能存储整形,因为泛型类型的实例为integer
		try {
			arrayList3.getClass().getMethod("add", Object.class).invoke(arrayList3, "asd");
			for(int i=0;i<arrayList3.size();i++){
				System.out.println(arrayList3.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public static void main(String[] args) {
		TypeErase te=new TypeErase();
		//te.test();
		te.test1();
		
	}
}
