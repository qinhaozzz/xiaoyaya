package com.lim.xyyutil.test;

import com.lim.xyyutil.basebean.BankCount;
import com.lim.xyyutil.basebean.BlockBankCount;
import com.lim.xyyutil.basebean.SynBankCount;
import org.junit.Test;

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
        threadPool.execute(() -> {
            while (true) {
                blockBank.add(100);
                blockBank.query();
            }
        });
        threadPool.execute(() -> {
            while (true) {
                blockBank.sub(100);
                blockBank.query();
            }
        });
        threadPool.shutdown();
    }
}
