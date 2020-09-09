package cacheline;

/**
 * @author xiaohei
 * @create 2020-08-21 上午11:00
 **/
public class Test2 {

    public static void main(String[] args) {
        int[] arr = new int[64 * 1024 * 1024];
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) arr[i] *= 3;
        System.out.println("第一个循环="+(System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i += 16) arr[i] *= 3;
        System.out.println("第二个循环="+(System.currentTimeMillis() - start));
    }
}
