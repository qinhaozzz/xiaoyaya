package com.lim.xyyutil.code.util;

import java.util.Random;

/**
 * @author qinhao
 */
public class AuditLogIdIncrease {

    private static AuditLogIdIncrease instance;

    private final int threadIdBits = 4;// 线程号位数
    private final int maxRandomId = 9;// 随机数最大值

    private AuditLogIdIncrease() {
    }

    public static AuditLogIdIncrease getInstance() {
        if (instance == null) {
            instance = new AuditLogIdIncrease();
        }
        return instance;
    }

    /**
     * 获取下一个ID 线程安全
     * ===> 单例模式下,对象锁相当于类锁
     * @param sysId
     * @return
     */
    public synchronized long nextId(int sysId) {
        return splice(sysId);
    }

    /**
     * 类锁
     * @param sysId
     * @return
     */
    public long nextId2(int sysId) {
        synchronized (AuditLogIdIncrease.class) {
            return splice(sysId);
        }
    }

    /**
     * 拼接id：时间戳+线程号+随机数+系统标识
     * @param sysId
     * @return
     */
    private long splice(int sysId) {
        String strLong = getCurrentTime() + getCurrentThreadId() + getRandomNum() + sysId;
        return Long.parseLong(strLong);
    }

    /**
     * 系统时间戳
     * @return
     */
    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前线程号
     * @return
     */
    private String getCurrentThreadId() {
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
    private int getRandomNum() {
        return new Random().nextInt(maxRandomId);
    }

    public static void main(String[] args) {

    }
}
