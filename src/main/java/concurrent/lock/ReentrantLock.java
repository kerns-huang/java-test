package concurrent.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 可重入锁
 *
 * @author xiaohei
 * @create 2020-03-25 下午2:18
 **/
public class ReentrantLock implements Lock {


    private AtomicReference<Thread> owner;
    /**
     * 被获取的次数
     */
    private AtomicInteger counter;

    private LinkedBlockingQueue<Thread> waitQueue;

    public ReentrantLock() {
        waitQueue = new LinkedBlockingQueue(100);
    }

    /**
     * 一直尝试获取锁
     */
    public void lock() {
        if (!tryLock()) {
            //如果获取不到锁，放入到等待队列
            waitQueue.offer(Thread.currentThread());
            while (true) {
                //取头部数据
                Thread head = waitQueue.peek();
                if (head == Thread.currentThread()) {
                    if (!tryLock()) {
                        //如果获取不到锁，说明是其它线程还占有的锁。挂起之后什么时候唤醒了，unlock的时候唤醒？
                        LockSupport.park();
                    } else {
                        //获取到了锁，直接弹出当前线程。
                        waitQueue.poll();
                        return;
                    }
                } else {
                    LockSupport.park();
                }
            }
        }


    }

    /**
     * 尝试获取锁，当被其它线程打断的时候，放弃获取锁
     *
     * @throws InterruptedException
     */
    public void lockInterruptibly() throws InterruptedException {

    }

    /**
     * 尝试获取锁，获取不到锁，直接返回false
     *
     * @return
     */
    public boolean tryLock() {
        int count = counter.get();
        if (count > 0) {
            if (owner.get() == Thread.currentThread()) {
                //拥有人是当前当前线程
                counter.compareAndSet(count, count + 1);
                return true;
            } else {
                //把当前线程放到等待队列里面
                return false;
            }
        } else {
            if (counter.compareAndSet(count, count + 1)) {
                //如果当前线程能够设置成1，设置所有人为当前现线程。
                owner.set(Thread.currentThread());
                return true;
            } else {
                //被其它的线程抢占，设置了，直接返回false
                return false;
            }
        }
    }

    /**
     * 超过多少时间没有获取到锁，就返回false
     *
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if(owner.get()!=Thread.currentThread()){
            //TODO 抛出异常
            throw new IllegalMonitorStateException("当前线程不是执行线程");
        }
        int count=    counter.decrementAndGet();
        if(count<=0){
            owner.set(null);
        }
        return true;
    }

    /**
     * 解除锁定
     */
    public void unlock() {

    }

    public Condition newCondition() {
        return null;
    }


}
