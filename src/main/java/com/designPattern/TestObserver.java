package com.designPattern;

import java.util.Enumeration;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Vector;

//观察者相当于事件监听者,被观察者相当于事件源好事件,执行逻辑时,通知observer即可触发oberver的update,同时可传被观察者和参数
public class TestObserver {
	public void tesDemoSource(){
		DemoSource ds=new DemoSource();
		DemoListener1 listener1=new DemoListener1();
		ds.addDemoListener(listener1);
		ds.addDemoListener(new DemoListener() {
			@Override
			public void handleEvent(DemoEvent dm) {
					System.out.println("事件来自匿名类...");
			}
		});
		
		ds.notifyDemoEvent();//触发事件,通知监听器
	}
	
	public static void main(String[] args) {
		TestObserver t=new TestObserver();
		t.tesDemoSource();
	}
}


class DemoSource{
	private Vector repository =new Vector<>(); //监听自己的监听队列
	public DemoSource(){
		
	}
	
	public void addDemoListener(DemoListener dl){
		repository.addElement(dl);
	}
	
	//通知所有的监听器
	public void notifyDemoEvent(){
		Enumeration enumi =repository.elements();
		while(enumi.hasMoreElements()){
			DemoListener dl=(DemoListener)enumi.nextElement();
			dl.handleEvent( new DemoEvent(this));
		}
	}
	
}

class DemoEvent extends EventObject{

	public DemoEvent(Object source) {
		//source事件源对象,如界面上发生的点击按钮事件中的按钮
		//所有EVENT在构造时都引用了对象source,该逻辑上认为该对象是最初发生有关Event的对象
		super(source);	
	}
	
	public void say(){
		System.out.println("This is say method....");
	}
}


interface DemoListener extends EventListener{
	//EventListener 是所有事件侦听接口必须扩展的标记接口,因为它是无内容的标记接口
	//所以事件处理方法由我们自己声明如下:
	public void handleEvent(DemoEvent dm);
}

class DemoListener1 implements DemoListener{
	@Override
	public void handleEvent(DemoEvent de) {
				System.out.println("Insude listener1.....");
				de.say();//回调
	}
	
}