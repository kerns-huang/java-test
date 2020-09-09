package stackuse;

/**
 * 逃逸分析 & 栈上分配  jdk 版本 >= 1.6 默认开启。
 * @author xiaohei
 * @create 2020-08-07 下午9:02
 **/

public class UserTest {
    public static void alloc() {
        User user = new User();
        user.setId("12123");
        user.setName("1231231");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024 * 1024; i++) {
            alloc();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
