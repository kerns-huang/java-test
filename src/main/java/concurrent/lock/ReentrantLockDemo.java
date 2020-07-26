package concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaohei
 * @create 2020-07-26 下午4:12
 **/
public class ReentrantLockDemo {
    public static void main(String[] args){
        final ReentrantLock reentrantLock=new ReentrantLock();
        reentrantLock.lock();
        System.out.println("主线程第一次获取锁");

        reentrantLock.lock();
        System.out.println("主线程第二次获取锁");
        reentrantLock.unlock();
        new Thread(()->{
            reentrantLock.lock();
            System.out.println("线程1第一次获取锁");
        }).start();
    }
}
