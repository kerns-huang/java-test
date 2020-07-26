package falseshare;

/**
 * @author xiaohei
 * @create 2020-06-28 下午4:06
 **/
public class Test {

    public static void main(String[] args) {
        int[][] array = new int[64 * 1024][1024];
        long start = System.currentTimeMillis();
        for (int i = 0; i < 64 * 1024; i++)
            for (int j = 0; j < 1024; j++)
                array[i][j]++;
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        for (int i = 0; i < 1024; i++)
            for (int j = 0; j < 64 * 1024; j++)
                array[j][i]++;
        System.out.println(System.currentTimeMillis() - start);
    }
}
