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

    private LinkedBlockingQueue waitQueue;

    public ReentrantLock() {
        waitQueue = new LinkedBlockingQueue(100);
    }

    /**
     * 一直尝试获取锁
     */
    public void lock() {
        int count = counter.get();
        if (count > 0) {
            if (owner.get() == Thread.currentThread()) {
                //拥有人是当前当前线程
                counter.compareAndSet(count, count + 1);
            } else {
                //把当前线程放到等待队列里面
                waitQueue.offer(Thread.currentThread());
                LockSupport.park();
            }
        } else {
            if (counter.compareAndSet(0, 1)) {
                //如果当前线程能够设置成1，设置所有人为当前现线程。
                owner.set(Thread.currentThread());
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
                return  true;
            } else {
                //把当前线程放到等待队列里面
               return false;
            }
        } else {
            if (counter.compareAndSet(0, 1)) {
                //如果当前线程能够设置成1，设置所有人为当前现线程。
                owner.set(Thread.currentThread());
                return true;
            }else {
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
        return false;
    }

    /**
     * 解除锁定
     */
    public void unlock() {

    }

    public Condition newCondition() {
        return null;
    }

    abstract static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 当前锁拥有人
         */
        private volatile Thread owner;


    }
}
