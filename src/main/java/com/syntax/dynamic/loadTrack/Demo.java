package com.syntax.dynamic.loadTrack;

//在 java 命令行上设置参数 -verbose:class 会打印类装入过程的跟踪记录
public class Demo {
	public static void main(String[] args) {  
		System.out.println(args);
		 System.out.println("**beginning execution**"); 
	        Greeter greeter = new Greeter(); 
	        System.out.println("**created Greeter**");
	        greeter.greet();
	        System.out.println("**************************************************************");
	        Greeter greeter2 = new Greeter(); 
	        greeter2.greet();
	    
	}
}
