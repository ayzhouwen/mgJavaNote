package com.meConcurrent;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
public class EnhancedUniqueIdTest {
    // 用于存储所有生成的ID和出现次数
    private static final Map<String, Integer> idMap = new ConcurrentHashMap<>();
    // 统计重复ID的数量
    private static final AtomicInteger duplicateCount = new AtomicInteger(0);
    // 统计总共生成的ID数量
    private static final AtomicInteger totalCount = new AtomicInteger(0);
    // 统计在相同时间戳下生成的ID数量（用于极端测试）
    private static final AtomicInteger sameMillisCount = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        testNormalScenario();
        testHighConcurrencyScenario();
        testSameTimestampScenario();
    }

    // 测试正常场景
    private static void testNormalScenario() throws InterruptedException {
        System.out.println("=== 测试1: 正常多线程场景 ===");
        runTest(10, 10000);
    }

    // 测试高并发场景
    private static void testHighConcurrencyScenario() throws InterruptedException {
        System.out.println("\n=== 测试2: 高并发场景 ===");
        runTest(50, 20000);
    }

    // 测试相同时间戳场景（模拟时间戳不变的情况）
    private static void testSameTimestampScenario() throws InterruptedException {
        System.out.println("\n=== 测试3: 相同时间戳极端测试 ===");

        int threadCount = 10;
        int iterations = 5000;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 使用固定的时间戳来测试UUID部分的唯一性
        final long fixedTimestamp = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < iterations; j++) {
                        String id = fixedTimestamp + "_" + UUID.randomUUID().toString().substring(0, 8);
                        totalCount.incrementAndGet();
                        sameMillisCount.incrementAndGet();

                        if (!idMap.containsKey(id)) {
                            idMap.put(id, 1);
                        } else {
                            duplicateCount.incrementAndGet();
                            System.out.println("相同时间戳下发现重复ID: " + id);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        System.out.println("相同时间戳测试 - 总生成数: " + sameMillisCount.get() +
                ", 重复数: " + duplicateCount.get());
    }

    private static void runTest(int threadCount, int iterations) throws InterruptedException {
        idMap.clear();
        duplicateCount.set(0);
        totalCount.set(0);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(new TestTask(iterations, latch, "Thread-" + i));
        }

        latch.await();
        executor.shutdown();

        long endTime = System.currentTimeMillis();

        System.out.println("线程数: " + threadCount + ", 每线程生成: " + iterations);
        System.out.println("总计: " + totalCount.get() + " IDs");
        System.out.println("唯一: " + idMap.size() + " IDs");
        System.out.println("重复: " + duplicateCount.get() + " IDs");
        System.out.println("耗时: " + (endTime - startTime) + "ms");
    }

    private static String generateId() {
        return System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
//        return System.currentTimeMillis()+"";
    }

    static class TestTask implements Runnable {
        private final int iterations;
        private final CountDownLatch latch;
        private final String threadName;

        public TestTask(int iterations, CountDownLatch latch, String threadName) {
            this.iterations = iterations;
            this.latch = latch;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < iterations; i++) {
                    String id = generateId();
                    totalCount.incrementAndGet();

                    synchronized (idMap) {
                        if (!idMap.containsKey(id)) {
                            idMap.put(id, 1);
                        } else {
                            duplicateCount.incrementAndGet();
                            // 取消注释可以看到具体重复信息
                            // System.out.println(threadName + " 重复: " + id);
                        }
                    }

                    // 微小的延迟，让时间戳有机会变化
                    if (i % 100 == 0) {
                        Thread.yield();
                    }
                }
            } finally {
                latch.countDown();
            }
        }
    }
}