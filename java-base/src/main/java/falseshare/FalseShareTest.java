package falseshare;

/**
 * 伪共享问题：
 * @author xiaohei
 * @create 2020-06-28 上午11:18
 **/
public class FalseShareTest implements Runnable {
    public static int NUM_THREADS = 4;
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;
    private static VolatileLong[] longs;
    public static long SUM_TIME = 0l;
    public FalseShareTest(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }
    public static void main(final String[] args) throws Exception {
        Thread.sleep(10000);
        for(int j=0; j<10; j++){
            System.out.println(j);
            NUM_THREADS=Runtime.getRuntime().availableProcessors();
            longs = new VolatileLong[NUM_THREADS];
            for (int i = 0; i < longs.length; i++) {
                longs[i] = new VolatileLong();
            }
            final long start = System.nanoTime();
            runTest();
            final long end = System.nanoTime();
            SUM_TIME += end - start;
        }
        System.out.println("平均耗时："+SUM_TIME/10);
    }
    private static void runTest() throws InterruptedException {
        //开启线程
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseShareTest(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }
    public final static class VolatileLong {
        public volatile long value = 0L;     //对所有线程可见
        /**
         * 对象头信息占用8到12个字节
         * 解决伪共享问题的关键
         * 缓存行通常是 64 字节（基于 64 字节，其他长度的如 32 字节）
         *
         */
        public long p1, p2, p3, p4, p5, p6;     //屏蔽此行
    }
}