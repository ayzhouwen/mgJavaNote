package com.nio.TcpServerdemo.pool;

import java.nio.channels.SelectionKey;

public class SelectServerThreadPool extends SelectorServer {
    private static  final  int MAX_THREADS=5;
    private ThreadPool pool=new ThreadPool(MAX_THREADS);

    public static void main(String[] args) {
        new SelectServerThreadPool().start(new String[]{"6543"} );
    }

    public  void  readDataFromClient(SelectionKey key) throws Exception{
        WorkerThread worker=pool.getWorker();
        if (worker == null){
            return;
        }
        worker.serviceChannel(key);
    }
}


