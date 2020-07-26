package concurrent.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteLockTest {

    volatile long i=0;
    ReentrantReadWriteLock lock=new ReentrantReadWriteLock();

    public void read(){
        lock.readLock().lock();
        i+=1;
        lock.readLock().unlock();
    }

}