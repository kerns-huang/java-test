package concurrent.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 读写锁，
 * 读锁可以把多个线程持有
 * 写锁只有一个线程持有。
 *
 * @author xiaohei
 * @create 2020-08-01 下午1:36
 **/
public class LocalReentrantReadWriteLock {
    /**
     * 写锁
     */
    private static final int WRITE_LOCK = 2;
    /**
     * 读锁
     */
    private static final int READ_LOCK = 1;
    /**
     * 写锁的持有线程
     */
    private AtomicReference<Thread> owner = new AtomicReference<>();
    /**
     * 阻塞队列
     */
    private LinkedBlockingQueue<WaitNode> linkedBlockingQueue = new LinkedBlockingQueue<>(100);
    /**
     * 读锁
     */
    private ReadLock readLock;
    /**
     * 写锁
     */
    private WriteLock writeLock;

    /**
     * counter 前16 位置代表写锁，后16为代表读锁，读写的操作能够原子性的操作
     */
    private AtomicInteger counter = new AtomicInteger(0);


    public LocalReentrantReadWriteLock() {
        writeLock = new WriteLock(this);
        readLock = new ReadLock(this);
    }


    public ReadLock readLock() {
        return readLock;
    }

    public WriteLock writeLock() {
        return writeLock;
    }

    static class WaitNode {
        private Thread thread;
        //1: 读锁，2： 写锁
        private int lockType;
        //添加或者减少的counter次数
        private int arg;

        public WaitNode(Thread thread, int lockType) {
            this.thread = thread;
            this.lockType = lockType;
        }
    }

    static class WriteLock {
        public static final int STEP = 1 << 16;
        /**
         * 不能在这定一个counter ，当 写锁获取数据的时候，没办法保证 读锁不获取数据，所以最好是使用同一个变量
         */
        //private AtomicInteger counter = new AtomicInteger(0);

        private LocalReentrantReadWriteLock lock;

        //公用lock的一些变量
        protected WriteLock(LocalReentrantReadWriteLock lock) {
            this.lock = lock;
        }

        public void lock() {
            if (!tryLock()) {
                //如果尝试获取锁失败，说明有其它线程已经获取到了锁，进入等待队列等待
                lock.linkedBlockingQueue.offer(new WaitNode(Thread.currentThread(), WRITE_LOCK));
                //查看头部节点owner是否为被设置为空，为空的话头部节点进行消费
                for (; ; ) {
                    WaitNode waitNode = lock.linkedBlockingQueue.peek();
                    //waitNode拥有不会为空，因为自身已经塞入等待队列里面去了
                    if (waitNode.thread == Thread.currentThread()) {
                        //如果头部线程就是当前线程，在获取一次数据
                        if (tryLock()) {
                            //如果能够获取到锁，直接弹出等待节点
                            lock.linkedBlockingQueue.poll();
                            return;
                        } else {
                            LockSupport.park();
                        }
                    } else {
                        LockSupport.park();
                    }
                }
            }

        }

        /**
         * 尝试获取写锁的时候，先要判断是counter是否是0 ，如果不为0，说明总有读锁或者写锁占用，直接返回flase。
         *
         * @return
         */
        public boolean tryLock() {
            int count = lock.counter.get();
            if (lock.owner.get() == null && count == 0) {
                //如果当前没有写的线程，而且没有读锁,那么设置当前owner，切写count +1
                if (lock.counter.compareAndSet(count, count + STEP)) {
                    //先保证count是自己，因为counter可能被其它读写线程更改，所以先操作，保证是当前线程修改写count++，然后当前线程在设置成当前线程
                    lock.owner.set(Thread.currentThread());
                }
                return false;
            } else if (Thread.currentThread() == lock.owner.get()) {
                //如果锁的拥有人是当前线程，说明只有自己这个写线程在写东西
                if (lock.counter.compareAndSet(count, count + STEP)) {
                    return true;
                }
                return false;
            }
            return false;
        }

        private boolean tryUnLock() {
            int count = lock.counter.get();
            if (count > STEP) {
                if (Thread.currentThread() != lock.owner.get()) {
                    throw new IllegalMonitorStateException("不能解锁不是本线程的锁");
                }
                int temp = count - STEP;
                if (lock.counter.compareAndSet(count, temp)) {
                    //按照道理有读锁应该是走入不到这一步的,这里面读锁的问题怎么规避掉
                    if (temp == 0) {
                        //如果不等于，说明还有着写锁存在
                        lock.owner.set(null);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return false;
        }

        public void unlock() {
            if (tryUnLock()) {
                //尝试解锁成功，唤醒头部节点
                WaitNode waitNode = lock.linkedBlockingQueue.peek();
                if (waitNode != null) {
                    LockSupport.unpark(waitNode.thread);
                }
            }

        }


    }

    static class ReadLock {

        private LocalReentrantReadWriteLock lock;

        protected ReadLock(LocalReentrantReadWriteLock lock) {
            this.lock = lock;
        }

        public void lock() {
            if (!tryLock()) {
                //尝试获取锁，如果不成公
                lock.linkedBlockingQueue.offer(new WaitNode(Thread.currentThread(), 1));
                //检查在这个时间端，是否当前线程已经释放了排他锁
                WaitNode waitNode= lock.linkedBlockingQueue.peek();
                LockSupport.park();
            }
        }

        /**
         * 读锁尝试获取锁的时候，需要判断是否有被写锁占用，如果写锁占用，判断是否是当前线程占用
         *
         * @return
         */
        public boolean tryLock() {
            //没有写线程，直接获取读锁
            if (lock.owner.get() == null || lock.owner.get() == Thread.currentThread()) {
                int count = lock.counter.get();
                //如果读锁可以修改成功，代表锁可以获取
                if (lock.counter.compareAndSet(count, count + 1)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        /**
         * 尝试解锁读锁
         *
         * @return
         */
        private boolean tryUnLock() {
            /**
             * 解锁的时候，当前是否存在写线程，如果写线程存在，没办法解锁。
             */
            for(;;) {
                int count = lock.counter.get();
                //判断是否有写锁在
                if (lock.counter.compareAndSet(count, count - 1)) {
                    //判断
                    return count == 0;
                }
            }
        }

        public void unlock() {


        }
    }


}
