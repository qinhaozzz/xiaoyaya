package com.lim.xyyutil.code.util;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author qinhao
 */
public class AuditLogIdIncreaseNew {
    private static final int threadIdBits = 4;// 线程号位数
    private static final int maxRandomId = 9;// 随机数最大值

    /**
     * 获取下一个ID 线程安全
     * ===> 类锁
     * @param sysId
     * @return
     */
    public static synchronized long nextId(int sysId) {
        return splice(sysId);
    }


    /**
     * 拼接id：时间戳+线程号+随机数+系统标识
     * @param sysId
     * @return
     */
    private static long splice(int sysId) {
        String strLong = getCurrentTime() + getCurrentThreadId() + getRandomNum() + sysId;
        return Long.parseLong(strLong);
    }

    /**
     * 系统时间戳
     * @return
     */
    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前线程号
     * @return
     */
    private static String getCurrentThreadId() {
        String strCurrentThreadId = String.format("%0" + threadIdBits + "d", Thread.currentThread().getId());
        if (strCurrentThreadId.length() > threadIdBits) {
            strCurrentThreadId = strCurrentThreadId.substring(0, threadIdBits);
        }
        return strCurrentThreadId;
    }

    /**
     * 获取随机数
     * @return
     */
    private static int getRandomNum() {
        return new Random().nextInt(maxRandomId);
    }

    public static void main(String[] args) {
        AtomicLong id = new AtomicLong(123L);
        System.out.println(id.getAndSet(11L));
    }
}
