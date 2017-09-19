package com.designPattern.compoent;

import java.util.List;

public class Leaf  extends Compoent{

	public Leaf(String name) {
		super(name);
	}

	@Override
	List<Compoent> getchildrenList() {
		return null;
	}

	@Override
	public void printNode(String preStr) {
	
			System.out.println(preStr+"-"+getName());
		
	}

}
