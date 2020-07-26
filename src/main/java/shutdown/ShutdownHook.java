package shutdown;

/**
 * 关闭之后的钩子
 *
 * @author xiaohei
 * @create 2020-07-21 上午9:25
 **/
public class ShutdownHook extends Thread {

    private boolean needShutDown = false;

    private Thread mainThread;

    public void run() {
        System.out.println("钩子线程已经接到退出信号");
        needShutDown = true;
        //打断主线程的关闭
        mainThread.interrupt();
        try {
            //等待主线程死亡
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO 正常情况下，这需要执行主线程死亡的回收任务，比如拒绝服务，线程池的关闭等操作
        System.out.println("钩子线程会在主线程死亡之后死去，结束钩子生活");
    }

    public ShutdownHook(Thread mainThread) {

        this.mainThread = mainThread;
        this.needShutDown = false;
        //在这添加关闭的钩子线程
        Runtime.getRuntime().addShutdownHook(this);
    }


    public boolean isShutDown() {
        return needShutDown;
    }
}
