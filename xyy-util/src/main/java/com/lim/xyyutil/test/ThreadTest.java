package com.lim.xyyutil.test;

import com.lim.xyyutil.basebean.BankCount;
import com.lim.xyyutil.basebean.BlockBankCount;
import com.lim.xyyutil.basebean.SynBankCount;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程
 * 1.生命周期
 * 2.互斥同步
 * 3.锁
 * @author qinhao
 */
public class ThreadTest {

    @Test
    public void test() {
        BankCount bank = new BankCount();
        SynBankCount synBank = new SynBankCount();
        BlockBankCount blockBank = new BlockBankCount();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        // 不加锁不同步
        // threadPool.execute(() -> {
        //     while (true) {
        //         bank.add(100);
        //         bank.query();
        //     }
        // });
        // threadPool.execute(() -> {
        //     while (true) {
        //         bank.sub(100);
        //         bank.query();
        //     }
        // });
        // 同步
        // threadPool.execute(() -> {
        //     while (true) {
        //         synBank.add(100);
        //         synBank.query();
        //     }
        // });
        // threadPool.execute(() -> {
        //     while (true) {
        //         synBank.sub(100);
        //         synBank.query();
        //     }
        // });
        // 加锁
        // threadPool.execute(() -> {
        //     while (true) {
        //         blockBank.add(100);
        //         blockBank.query();
        //     }
        // });
        // threadPool.execute(() -> {
        //     while (true) {
        //         blockBank.sub(100);
        //         blockBank.query();
        //     }
        // });
        // 可中断锁
        threadPool.execute(() -> {
            try {
                blockBank.testInterrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        /**
         * 对线程调用thread.interrupt方法能够中断等待
         */
        threadPool.execute(() -> {
            try {
                blockBank.testInterrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.shutdown();
    }

    /**
     * 有线程 T1、T2 和 T3。你如何确保 T2 线程在 T1 之后执行，并且 T3 线程在 T2 之后执行？
     * Thread.join():父线程等待子线程结束之后才能继续运行
     */
    @Test
    public void testJoin() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("我是线程t1，我需要第一个执行");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("我是线程t2，我需要第二个执行");
        });
        Thread t3 = new Thread(() -> {
            System.out.println("我是线程t3，我需要第三个执行");
        });
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }

    /**
     * Java实现阻塞队列
     */
    @Test
    public void testBlockQueue() {
        ArrayBlockingQueue<String> blockArray = new ArrayBlockingQueue<String>(5);
        try {
            blockArray.add("1222");
            System.out.println(blockArray.take());
            System.out.println("123");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
