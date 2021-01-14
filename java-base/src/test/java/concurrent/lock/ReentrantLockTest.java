package concurrent.lock;

class ReentrantLockTest {

    public static void main(String[] args) throws Exception {
        LocalReentrantLock localReentrantLock = new LocalReentrantLock();
        localReentrantLock.lock();
        Thread.sleep(1000l);
        System.out.println("主线程第一次获取锁");
        new Thread(() -> {
            localReentrantLock.lock();
            System.out.println("t1 线程第一次获取锁");
            localReentrantLock.unlock();
            System.out.println("t1 线程解锁他获取的锁");
        }).start();
        localReentrantLock.lock();
        Thread.sleep(1000l);
        System.out.println("主线程第二次获取锁");
        localReentrantLock.unlock();
        System.out.println("主线程第一次释放锁");
        localReentrantLock.unlock();
        System.out.println("主线程第二次释放锁");
        Thread.sleep(1000l);
    }

}