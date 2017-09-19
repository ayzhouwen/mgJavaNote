package com.meConcurrent.cancelTask;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//测试利用重写AbstractExecutorService 的 newTaskFor 方法来实现任务的取消 ,此接口最好单独写一个文件,否则是内部类调用不方便
interface CancellableTask<T> extends Callable<T> {
	void cancel();

	RunnableFuture<T> newTask();
}
//此类最好单独写一个文件,否则是内部类调用不方便
abstract class SocketUSingTask<T> implements CancellableTask<T> {
	private Socket socket;

	protected synchronized void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public synchronized void cancel() {
		try {
			System.out.println("重写的cancel执行");
			if (socket != null) {
				socket.close();
			}
			//Thread.currentThread().interrupt();
		} catch (IOException e) {
		}
	}

	@Override
	public RunnableFuture<T> newTask() {
		return new FutureTask<T>(this) {
			public boolean cancel(boolean mayInterruptIfRunning) {
				try {
					SocketUSingTask.this.cancel();
				} finally {
					return super.cancel(mayInterruptIfRunning);
				}

			}

		};

	}

	public    static  class CancellingExecutor extends ThreadPoolExecutor {
		public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> arrayBlockingQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, arrayBlockingQueue);
		}



		


		protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
			if (callable instanceof CancellableTask) {
				return ((CancellableTask<T>) callable).newTask();
			} else {
				return super.newTaskFor(callable);
			}
		}
		
	
	}

}
