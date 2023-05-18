package com.meConcurrent.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *  ForkJoinPool测试
 *  参考: https://mp.weixin.qq.com/s/mxlhR7KaWlWXvLpm7eNS9Q
 */
@Slf4j
public class ForkJoinPoolTest {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = pool.submit(new SumTask(1, 1000));
        log.info("fork 循环计算  累加值：" + task.join());
    }
}

@Slf4j
class SumTask extends RecursiveTask<Long> {

    private final int start;
    private final int end;
    /**
     * 需要在构造该任务对象的时候，就告知求和的起点和终点分别是什么数，所以定义在构造器中
     */
    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 最重要的方法，该方法中就是具体的业务逻辑实现
     *
     */
    @Override
    protected Long compute() {
        log.info("当前start:{},end:{},差值:{}",start,end,end-start);
        if (end - start <= 50) {

            // 如果起止位置相差小于等于 50，则直接使用循环求解，并返回
            long result = 0;
            for (int i = start; i <= end; i++) {
                result += i;
            }
            log.info("当前start:{},end:{},求和值:{}",start,end,result);
            return result;
        } else {
            // 如果起止位置大于 50，将任务拆分，这里演示的是拆分成两个子任务（其实可以拆分成任意数量的）
            int middle = (start + end) / 2;
            // 创建两个子任务
            SumTask firstTask = new SumTask(start, middle);
            SumTask secondTask = new SumTask(middle + 1, end);
            // 通过 invokeAll 将多个子任务提交
            invokeAll(firstTask, secondTask);
            // 或者手动调用 fork 方法，下面两行是用来替代 invokeAll 那一行的
            // 但这样的写法是不推荐的，之后会解释
             firstTask.fork();
             secondTask.fork();
            // 使用 join 方法等待，子任务的返回
            return firstTask.join() + secondTask.join();
        }
    }
}