package ratelimit;

/**
 * 滑动窗口限流，
 * 固定窗口限流有个缺陷，
 * 临界问题
 * 比如每分钟限制100个，我
 *
 * @author xiaohei
 * @create 2020-08-27 上午12:10
 **/
public class SlidingWindowLimiter {
    /**
     * 多少个时间窗口
     **/
    private int windowSize;
    /**
     * 队列的走过总长度
     **/
    private int timeSliceSize = 2000;
    /**
     * 没个时间片的时间长度
     */
    private int timeMillisPerSlice;
    /**
     * 支持多少个线程并发
     */
    private int threadHold;

    public SlidingWindowLimiter(int timeMillisPerSlice, int windowSize, int threadHold) {
        this.timeMillisPerSlice = timeMillisPerSlice;
        this.windowSize = windowSize;
        this.threadHold = threadHold;
    }


    public static void main(String[] args) {


    }
}
