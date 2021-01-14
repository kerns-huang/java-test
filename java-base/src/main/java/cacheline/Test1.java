package cacheline;

/**
 * @author xiaohei
 * @create 2020-06-28 下午4:06
 **/
public class Test1 {

    public static void main(String[] args) {
        int[][] array = new int[64 * 1024][1024];
        long start = System.currentTimeMillis();
        for (int i = 0; i < 64 * 1024; i++)
            for (int j = 0; j < 1024; j++)
                array[i][j]++;
        System.out.println("第一个循环="+(System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        array = new int[64 * 1024][1024];
        for (int i = 0; i < 1024; i++)
            for (int j = 0; j < 64 * 1024; j++)
                array[j][i]++;
        System.out.println("第二个循环="+(System.currentTimeMillis() - start));
    }
}
