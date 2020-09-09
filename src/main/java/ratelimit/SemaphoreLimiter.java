package ratelimit;

import java.util.concurrent.Semaphore;

/**
 * @author xiaohei
 * @create 2020-08-26 下午8:17
 **/
public class SemaphoreLimiter {

    public static void main(String[] args) {
        //公平线程，FIFO
        Semaphore semaphore = new Semaphore(5, true);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire(1);
                        System.out.println("获取了一个信号");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("获取许可失败");
                    } finally {
                        semaphore.release(1);
                    }

                }
            });
        }

    }
}
