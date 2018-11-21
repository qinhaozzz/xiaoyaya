package com.lim.xyyutil.basebean;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qinhao
 */
public class BlockBankCount {

    private Integer count = 0;
    private Lock lock = new ReentrantLock();

    public void add(Integer money) {
        lock.lock();
        try {
            count += money;
            System.out.println(Thread.currentThread().getName() + " add $" + money);
        } finally {
            lock.unlock();
        }
    }

    public void sub(Integer money) {
        lock.lock();
        try {
            if (count - money < 0) {
                System.out.println("not sufficient funds");
            } else {
                count -= money;
                System.out.println(Thread.currentThread().getName() + " sub $" + money);
            }
        } finally {
            lock.unlock();
        }
    }

    public void query() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " count $" + count);
        } finally {
            lock.unlock();
        }
    }
}
