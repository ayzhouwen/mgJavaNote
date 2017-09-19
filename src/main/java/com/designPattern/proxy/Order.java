package com.designPattern.proxy;

public class Order  implements OrderApi {

	private String pname;
	private String username;
	public Order(String pname ,String username){
		this.pname=pname;
		this.username=username;
	}
	@Override
	public String getProductName() {
		// TODO Auto-generated method stub
		return pname;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public void setProName(String pname,String username) {
		
		if (username.equals(this.username)) {
			System.out.println("审核通过,可以重新设置产品名称");
			this.pname=pname;
		}else {
			System.out.println("设置失败与原用户名不一致");
		}

	}
		@Override
		public String toString() {
				return pname+username;
		}
	}
