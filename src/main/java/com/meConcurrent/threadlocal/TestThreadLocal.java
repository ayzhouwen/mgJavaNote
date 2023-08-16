package com.meConcurrent.threadlocal;

//http://www.cnblogs.com/ilellen/p/4135266.html
// 0x61c88647是一个神奇的数字
public class TestThreadLocal {
		public static void main(String[] args) {
			P	P=new P();
			P1	P1=new P1();
			P.id=999;
			P1.name="狗熊";
				ThreadLocal<P>  tl1=new ThreadLocal();
				ThreadLocal<P1>  tl2=new ThreadLocal();
				
				tl1.set(P);
				tl2.set(P1);
				P1.name="猎豹";
				
				System.out.println(tl1.get().id);
				System.out.println(tl2.get().name);
				tl1.remove();
				
		}
}

class P {
	public int id;
}

class P1{
	public String name;
}
