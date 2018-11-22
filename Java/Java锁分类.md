Java中锁的分类
======
* 公平锁/非公平锁
* 可重入锁/非可重入锁
* 独享锁/共享锁
* 互斥锁/读写锁
* 乐观锁/悲观锁
* 分段锁
* 无锁/偏向锁/轻量级锁/重量级锁
* 自旋锁

乐观锁/悲观锁
------
悲观锁：悲观锁认为自己在使用数据的时候一定有别的线程来修改数据，因此在获取数据的时候会先加锁，确保数据不会被别的线程修改。

乐观锁：乐观锁认为自己使用数据时不会有别的线程修改数据，所以不会添加锁，只是在更新数据的时候去判断之前有没有别的线程更新了这个数据。如果这个数据没有被更新，当前线程将自己修改的数据成功写入。如果数据已经被其他线程更新，则根据不同的实现方式执行不同的操作（例如报错或者自动重试）。

悲观锁在Java中的使用，就是synchronized关键字和Lock的实现类。  
乐观锁在Java中的使用，是无锁编程，常常采用的是`CAS
算法(compare and swap)`，典型的例子就是原子类，通过CAS自旋实现原子操作的更新。
``` java
// ------------------------- 悲观锁的调用方式 -------------------------
// synchronized
public synchronized void test() {
	// 操作同步资源
}
// ReentrantLock
private ReentrantLock lock = new ReentrantLock(); // 需要保证多个线程使用的是同一个锁
public void modifyPublicResources() {
	lock.lock();
	// 操作同步资源
	lock.unlock();
}

// ------------------------- 乐观锁的调用方式 -------------------------
private AtomicInteger atomicInteger = new AtomicInteger();  // 需要保证多个线程使用的是同一个AtomicInteger
atomicInteger.incrementAndGet(); //执行自增1
```
