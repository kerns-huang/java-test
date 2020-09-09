package ratelimit;

/**
 * 计数器限流
 * 固定窗口限流。
 *
 * @author xiaohei
 * @create 2020-09-06 下午7:54
 **/
public class CounterLimit {
    /**
     * 限流的时间戳
     */
    private static long timestamp = System.currentTimeMillis();
    /**
     * 限制请求数目
     */
    private static long limitCount = 100;
    /**
     * 间隔时间
     */
    private static long interval = 1000;
    /**
     * 请求数
     */
    private static long reqCount = 0;

    public static boolean grant() {
        long now = System.currentTimeMillis();
        if (now < timestamp + interval) {
            if (reqCount < limitCount) {
                reqCount += 1;
                return true;
            } else {
                return false;
            }

        } else {
            timestamp = System.currentTimeMillis();
            reqCount = 1;
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (grant()) {
                        System.out.println("不限流");
                    } else {
                        System.out.println("限流");
                    }
                }
            }).start();
        }
        Thread.sleep(10000);
    }

}
