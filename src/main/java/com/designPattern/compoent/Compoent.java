package com.designPattern.compoent;
//组合模式来实现树形结构(叶子结构跟非叶子节点使用不同的类),跟链表类似

import java.util.List;

public  abstract  class Compoent {
	private String name;
	private String path; //从根节点到当前节点的路径,判断是否循环引用,发现循环引用则抛出异常
	
	
	 public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getName() {
		return name;
	}

	
	public Compoent(String name){
		 this.name=name;
	 }
	private  Compoent  parent=null;
	abstract List<Compoent> getchildrenList();
	public Compoent getParent() {
		return parent;
	}
	public void setParent(Compoent parent) {
		this.parent = parent;
	}
	
	public abstract void printNode(String preStr);
	
	//组合类先实现方法然后返回抛出异常,为了实现对外的透明性,和叶子类调用改方法,涉及到节点的操作都放在非叶子节点中
	public  void addChildren(Compoent node){
		throw  new UnsupportedOperationException("对象不支持这个方法");
	};
		
   public void removeChildren(Compoent node){
	   throw  new UnsupportedOperationException("对象不支持这个方法");
   }

	
   public static void main(String[] args) {
	   Compoent cp=new CompTree("生物");
	   Compoent cp1=new CompTree("动物");
	   Compoent cp2=new CompTree("植物");
	   Compoent cp3=new Leaf("人类");
	   Compoent cp4=new Leaf("兽类");
	   Compoent cp5=new Leaf("花");
	   Compoent cp6=new Leaf("草");
	   cp1.addChildren(cp3);
	   cp1.addChildren(cp4);
	   
	   cp2.addChildren(cp5);
	   cp2.addChildren(cp6);
	   
	   cp.addChildren(cp1);
	   cp.addChildren(cp2);
	   
	   cp.printNode("");
   }
	
}
