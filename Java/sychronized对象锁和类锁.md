sychronized中类锁和对象锁的区别
======
类锁和对象锁是两种不同的锁，对静态方法加锁或者使用sychronized（XX.class）相当于加了类锁，对实例方法加锁或者采用sychronized（this或对象）相当于加了对象锁，`如果是单例模式下，那么就是变成和类锁一样的功能。`两者区别在于对于同一个类的不同实例来说，如果加了类锁，那么在一个线程获得类锁后，其他线程即使是持有同一个类的不同的实例，也得等待类锁的释放，因为它们竞争的都是类锁；如果一个线程获得了对象锁，那么持有不同对象的线程则可以并发执行，因为虽然竞争的都是对象锁，但并不是同一把对象锁。既然类锁和对象锁是不同的锁，那么一个线程可以同时获取两把锁。

* 添加类锁的方式
```java
public Class Test{

  // 1.代码块方式
  public void test1(){
    synchronized(Test.class){
      // TODO
    }
  }

  // 2.静态方法方式
  public static synchronized test2(){
    // TODO
  }
}
```
* 添加对象锁的方式
```java
public Class Test{

  // 1.代码块方式
  public void test1(){
    synchronized(this){
      // TODO
    }
  }

  // 2.方法锁方式
  public synchronized void test2(){
    // TODO
  }
}
```
