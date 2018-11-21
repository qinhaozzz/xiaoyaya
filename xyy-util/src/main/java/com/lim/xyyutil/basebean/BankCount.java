package com.lim.xyyutil.basebean;

/**
 * @author qinhao
 */
public class BankCount {

    private Integer count = 0;

    public void add(Integer money) {
        count += money;
        System.out.println(Thread.currentThread().getName() + " add $" + money);
    }

    public void sub(Integer money) {
        if (count - money < 0) {
            System.out.println("not sufficient funds");
        } else {
            count -= money;
            System.out.println(Thread.currentThread().getName() + " sub $" + money);
        }
    }

    public void query() {
        System.out.println(Thread.currentThread().getName() + " count $" + count);
    }
}
