package shutdown;

/**
 * 测试主线程
 *
 * @author xiaohei
 * @create 2020-07-21 上午9:32
 **/
public class TestMain {

    private ShutdownHook shutdownHook;

    public static void main(String[] args){
       TestMain testMain=new TestMain();
       System.out.println("开始执行测试");
       testMain.exec();
       System.out.println("结束测试");
    }

    public TestMain(){
        //当前线程是主线程。
        shutdownHook=new ShutdownHook(Thread.currentThread());
    }

    public void exec(){
        while (!shutdownHook.isShutDown()){
            System.out.println("睡眠1秒钟");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("主线程睡眠被打断");
            }
            System.out.println("我已经活过来了");
        }
        System.out.println("关闭钩子已经执行完成");
    }


}
