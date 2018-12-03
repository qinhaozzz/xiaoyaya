Thread线程知识点
======

join()方法
------
**Thread中，调用方线程（调用join方法的线程）执行等待操作，直到被调用的线程（join方法所属的线程）结束，再被唤醒。**
> 题目：1. 如何使多个线程按照一定的顺序去执行?

```java
   @Test
   public void testDoubleLatch() {
       Thread t1 = new Thread(() -> {
           System.out.println("t1");
       });
       Thread t2 = new Thread(() -> {
           System.out.println("t2");
       });
       Thread t3 = new Thread(() -> {
           System.out.println("t3");
       });
       t1.start();
       t1.join();
       t2.start();
       t2.join();
       t3.start();
       t3.join();
   }
```
