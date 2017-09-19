package com.meConcurrent.concurrentTest;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.security.auth.login.AccountException;


public class YieldProblem {

	//Yield加上synchronized用户金额转账并没有出现错乱,但是去掉synchronized有问题,存在转账并发问题
	public static synchronized void transferCredits(User from, User to, double money) {
		int threshold = 500;
		from.setDeposit(from.getDeposit() - money);
		Random ra = new Random();
		if (ra.nextInt(1000) > threshold) {
			Thread.yield();
		}

		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		to.setDeposit(to.getDeposit() + money);

	}

	public static void main(String[] args) {
		// Random ra =new Random();
		// System.out.println(ra.nextInt(1000));
		User u1 = new User(2000);
		User u2 = new User(0);

		for (int j = 0; j < 1000; j++) {
			CountDownLatch cdLatch = new CountDownLatch(1000);
			for (int i = 0; i < 1000; i++) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						double m = 1;
						YieldProblem.transferCredits(u1, u2, m);
						// System.out.println("u1=>u2:"+m);

						cdLatch.countDown();
					}
				}).start();
			}

			try {
				cdLatch.await();
				System.out.println("u1:" + u1.getDeposit() + ",u2:" + u2.getDeposit());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class User {

	private int id;
	private double deposit; // 存款

	User(double deposit) {
		this.deposit = deposit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

}