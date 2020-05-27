package com.meConcurrent.lock;

//死锁测试
public class DeadLock {
	// 警告,易产生死锁
	// money:转账金额
	private static final Object tieLock=new Object(); //当fromAccount与toAccount的hashCode相等时,需要此锁
	public static void transferMoney(Account fromAccount, Account toAccount, double money) {
		synchronized (fromAccount) {
			synchronized (toAccount) {
				if (fromAccount.getBalance() - money < 0) {
					throw new RuntimeException("余额不足");
				} else {
					fromAccount.reduceMoney(money);
					toAccount.addMoney(money);
				}
			}
		}
	}

	// 通过hashcode的大小来,使用不同的锁,尽量来避免死锁
	public static void transferMoney2(Account fromAccount, Account toAccount, double money) {
		class Helper {
			public void transfer() {
				if (fromAccount.getBalance() - money < 0) {
					throw new RuntimeException("余额不足");
				} else {
					fromAccount.reduceMoney(money);
					toAccount.addMoney(money);
				}

			}
		}

		int fromHash = fromAccount.hashCode();
		int toHash = toAccount.hashCode();
		if (fromHash < toHash) {
			synchronized (fromAccount) {
				synchronized (toAccount) {
					new Helper().transfer();
				}
			}
		} else if (fromHash > toHash) {
			synchronized (toAccount) {
				synchronized (fromAccount) {
					new Helper().transfer();
				}
			}
		}else if (fromHash==toHash) {
			synchronized (tieLock) {
				synchronized (fromAccount) {
					synchronized (toAccount) {
						new Helper().transfer();
					}
				}
			}
		} 
		
	
		

	}

	// 通过多线程来模拟transferMoney带来的死锁,死锁可以用visualVM来发现死锁
	public static void testDeadLock() {
		Account from = new Account(1, 100);
		Account to = new Account(2, 200);
		int i = 0;
		while (i < 100) {
			i++;
			try {
				Thread.sleep(50);
				new Thread(new Runnable() {
					@Override
					public void run() {
						transferMoney(from, to, 10); //发现死锁
						//transferMoney2(from, to, 10); //没发现死锁
					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						transferMoney(to, from, 30);//发现死锁
						//transferMoney2(to, from, 30);//没发现死锁
					}
				}).start();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		testDeadLock();

	}
}

class Account {
	private double balance = 0; // 余额
	private int uid;// 用户id

	Account(int uid, double balance) {
		this.uid = uid;
		this.balance = balance;
	}

	// 增加存款
	public void addMoney(double money) {
		balance = balance + money;
	}
	// 减少存款

	public void reduceMoney(double money) {
		balance = balance - money;
	}

	// 获取当前存款
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}