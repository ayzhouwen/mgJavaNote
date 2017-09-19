package syntax;

import java.util.ArrayList;
import java.util.List;
//诡异代码汇总
public class Strange {
	//1.未指定类型竟然可以插入任意类型()
	//2.不管list是否指定类型,调试的时候显示的都是元素都是object,泛型在java中的运行时都是擦除的
	//println有很多重载,报错虫子走的是参数是string,
	public void strangeList(){

		List lst = new ArrayList<String>();  
		List<String> list2=new ArrayList<String>();
		//list2.add(4.5); //指定类型会报错
		list2.add("a"); list2.add("b");
	    lst.add( 1 );  
	    lst.add("Test004");  
	    lst.add(0.5);
	    lst.add(new Strange());
	    List<Integer> ls = lst ;   //注意跟下面 List<String> ls1 = lst1 ; 的类型区别
	    ls.add(2);    
	    for (int i = 0; i < ls.size(); i++) {  
	        System.err.println(ls.get(i));  
	    }  
	    System.err.println( "----------" ) ;   
	   List lst1 = new ArrayList<String>();  
	   lst1.add("Test004") ;   
	   lst1.add(1);  
	   List<String> ls1 = lst1 ;    //区别在这里,如何指定String那么转换直接报错
	   ls1.add("Test0041");   
	   
	   for (int i = 0; i < ls1.size(); i++) {  
	       System.err.println( ls1.get(i) ) ;     //这里会报错,跟println实现有关
	   }  

	}
public static void main(String[] args) {
	Strange s=new Strange();
	s.strangeList();
}
}
