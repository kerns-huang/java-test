package concurrent.lock;


public class LocalReentrantReadWriteLockTest {

    private volatile int i=0;

    private LocalReentrantReadWriteLock localReentrantReadWriteLock;

    public void read(){
        localReentrantReadWriteLock.readLock().lock();
        System.out.println(i);
        localReentrantReadWriteLock.readLock().unlock();
    }


    public void write(){
        localReentrantReadWriteLock.writeLock().lock();
        i++;
        localReentrantReadWriteLock.writeLock().unlock();
    }

}