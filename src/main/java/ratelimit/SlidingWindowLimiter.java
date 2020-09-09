package ratelimit;

/**
 * 滑动窗口限流，
 * 固定窗口限流有个缺陷，
 * 临界问题
 * 比如每分钟限制100个，我
 * <p>
 * 将时间分为多个时区
 * 在每个区间内每有一次请求，将计数+，维持一个时间窗口，占据多个区间。
 * 每经过一个区间的时间，则抛弃最老的一个区间，并纳入新的区间
 * 如果当前窗口内区间的请求计数综合超过了限制数量，则本窗口所有请求都被移除。
 *
 * @author xiaohei
 * @create 2020-08-27 上午12:10
 **/
public class SlidingWindowLimiter {
    /**
     * 每个窗口的请求数
     */
    private int[] windowRequestNum;
    /**
     * 多少个时间窗口
     **/
    private int windowSize;
    /**
     * 队列的走过总长度
     **/
    private int timeSliceSize = 2000;
    /**
     * 每个时间片的时间长度
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
