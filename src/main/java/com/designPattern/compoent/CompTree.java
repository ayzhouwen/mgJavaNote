package com.designPattern.compoent;

import java.util.ArrayList;
import java.util.List;

public class CompTree extends Compoent  {
	public CompTree(String name) {
		super(name);
	}

	private List<Compoent> clist=new ArrayList<>(); 
	@Override
	List<Compoent> getchildrenList() {
		// TODO Auto-generated method stub
		return clist;
	}

	@Override
	public void printNode(String preStr) {

		System.out.println(preStr+" +"+this.getName());
		if(this.getchildrenList() !=null){
			
	
		preStr+="       ";
		for(int i=0;i<this.getchildrenList().size() ;i++){
		
			Compoent node=this.getchildrenList().get(i);
		
				node.printNode(preStr);
		
		}
		
		}
	
	}
	
	

		
	//新增对象时要设置其父节点的引用
	public  void addChildren(Compoent node){
			if(node ==null ){
				return;
			}
			node.setParent(this);
			clist.add(node);
	};
	
	//删除对象时要将设置删除节点的子节点的父节点为当前节点
   public void removeChildren(Compoent node){
		if(node ==null ||node.getchildrenList().size()>0){
			return;
		}
		
		for(int i=0;i<node.getchildrenList().size();i++){
			Compoent subc=node.getchildrenList().get(i);
			subc.setParent(this);
		}
   }
}
