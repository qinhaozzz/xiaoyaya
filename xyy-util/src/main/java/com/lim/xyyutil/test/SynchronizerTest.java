package com.lim.xyyutil.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * 同步器，主要协助线程同步
 * @author qinhao
 */
@Slf4j
public class SynchronizerTest {

    public static int num = 0;

    /**
     * 闭锁
     * :主要用于让一个主线程等待一组事件（countDown()方法）发生之后继续执行。
     * 注意：其他线程调用完countDown()方法之后是会继续执行的。
     */
    @Test
    public void testCountDownLatch() throws InterruptedException {
        int count = 2000;
        final CountDownLatch latch = new CountDownLatch(count);
        ExecutorService threadPool = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    num++;
                    log.info("thread: " + Thread.currentThread().getId());
                    latch.countDown();
                }
            });
        }
        latch.await();
        threadPool.shutdown();
        log.info("testCountDownLatch end。{}", num);
    }

    @Test
    public void testCyclicBarrier() throws BrokenBarrierException, InterruptedException {
        int count = 20;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        ExecutorService threadPool = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    log.info("i am coming");
                    try {
                        cyclicBarrier.await();
                        log.info("i am finished。{}");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
