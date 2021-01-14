import com.sun.istack.internal.NotNull;

/**
 * @author xiaohei
 * @create 2020-12-24 下午4:52
 **/
public class test {
    public static void main(String[] args) {
        Integer a=null;
        int b=a;
        method(b);
    }

//    public static void method(@NotNull String param) {
//        switch (param) {
//            // 肯定不是进入这里
//            case "sth":
//                System.out.println("it's sth");
//                break;
//            // 也不是进入这里
//            case "null":
//                System.out.println("it's null");
//                break;
//            // 也不是进入这里
//            default:
//                System.out.println("default");
//        }
//    }


    public static void method( int param) {
        switch (param) {
            // 肯定不是进入这里
            case 1:
                System.out.println("it's sth");
                break;
            // 也不是进入这里
            case 2:
                System.out.println("it's null");
                break;
            // 也不是进入这里
            default:
                System.out.println("default");
        }
    }
}
