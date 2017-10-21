package com.nio.TcpServerdemo.pool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

//工作线程
public class WorkerThread extends  Thread {
    private ByteBuffer buffer=ByteBuffer.allocate(1024);
    private ThreadPool pool;
    private SelectionKey key;

    WorkerThread(ThreadPool pool){
        this.pool=pool;
    }

    public  synchronized  void  run(){
        System.out.println(this.getName()+"  已准备");
        while (true){
            try {
                this.wait(); //休眠并且释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.interrupted();
            }

            if (key==null){
                continue; //以防万一
            }

            System.out.println(this.getName()+ "  已被唤醒");

            try {
                drainChannel(key);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    key.channel().close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                key.selector().wakeup();
            }
            key=null;
            this.pool.returnWorker(this);
        }
    }
        //消费通道数据
    void  drainChannel(SelectionKey key) throws  Exception{
        SocketChannel channel=(SocketChannel) key.channel();
        int count;
        buffer.clear(); //清空缓存
        //
        while ((count=channel.read(buffer)) >0){
            buffer.flip();
                while (buffer.hasRemaining()){
                    channel.write(buffer);
                }
                buffer.clear();
        }


        if (count<0){
            channel.close();
            return;
        }

        key.interestOps(key.interestOps()|SelectionKey.OP_READ); //重新设置感兴趣
        key.selector().wakeup(); //唤醒选择器,以至于这个key能够被再次激活



    }

    //消除读操作,并唤醒线程
    synchronized void  serviceChannel(SelectionKey key){
        this.key=key;
        key.interestOps(key.interestOps()& (~SelectionKey.OP_READ));
        this.notify(); //唤醒线程
    }

}
