package com.meConcurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
//注意:函数接口只能定义一个正常的方法名,否则在用这个函数接口会提示函数接口无效

@FunctionalInterface
interface IMyLmda{
	 public     <T>   List<T>  PThis(IMyLmda e);  //限制只有一个接口函数
}
@FunctionalInterface  //限制只有一个接口函数
interface IMyLmda1{
	 public   void  PThis(String e); //必须只有一个成员函数名,否则一直报不是函数类型
}
//邪恶的lamada
public class TestLamada implements  IMyLmda {
public static void main(String[] args) {
	String[] atp = {"Rafael Nadal", "Novak Djokovic",
		       "Stanislas Wawrinka",
		       "David Ferrer","Roger Federer",
		       "Andy Murray","Tomas Berdych",
		       "Juan Martin Del Potro"};
	
	List<String> players=Arrays.asList(atp);
	players.forEach((e)->{System.out.println(e+";");
	System.out.println();
	}); //e为回调元素
	
	TestLamada tl=new TestLamada();

	
	IMyLmda1 r1=(String e) -> {System.out.println("Hello Lambda!");};

	tl.PThis(  (IMyLmda1) -> {System.out.println("Hello Lambda!");} );
	
}

public void PThis(Object e) {
	// TODO Auto-generated method stub
	
}


public <T> List<T> PThis(IMyLmda1  e) {
	// TODO Auto-generated method stub
	//Runnable
	//Callable
	
	List<Integer> aIntegers=new ArrayList<>();
	 return (List<T>) aIntegers;
}


@Override
public <T> List<T> PThis(IMyLmda  e) {
	// TODO Auto-generated method stub
	//Runnable
	//Callable
	
	List<Integer> aIntegers=new ArrayList<>();
	 return (List<T>) aIntegers;
}
 


}
