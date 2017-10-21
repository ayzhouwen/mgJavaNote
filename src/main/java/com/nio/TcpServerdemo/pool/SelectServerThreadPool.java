package com.nio.TcpServerdemo.pool;

import java.nio.channels.SelectionKey;

//线程池版server

//2017年10月19日16:40:21,nio书中带线程池的例子,注意这个线程池不是
//官方的线程池,是作者diy简单的池子,生产上要用官方的池子
public class SelectServerThreadPool extends SelectorServer {
    private static  final  int MAX_THREADS=5;
    private ThreadPool pool=new ThreadPool(MAX_THREADS);

    public static void main(String[] args) {
        new SelectServerThreadPool().start(new String[]{"6666"} );
    }

    public  void  readDataFromClient(SelectionKey key) throws Exception{
        WorkerThread worker=pool.getWorker();
        if (worker == null){
            return;
        }
        worker.serviceChannel(key);
    }
}


