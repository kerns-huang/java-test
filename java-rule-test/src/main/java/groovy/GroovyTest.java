package groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * @author xiaohei
 * @create 2021-01-14 上午10:06
 **/
public class GroovyTest {

    public static void main(String args[]) {
        Binding binding = new Binding();
        binding.setVariable("x", 10);
        binding.setVariable("language", "Groovy");
        GroovyShell shell = new GroovyShell(binding);
        long start= System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Object value = shell.evaluate("y = x * 2; z = x * 3; return x ");
        }
        System.err.println( "groovy 消耗时间 " + (System.currentTimeMillis()-start)+"毫秒");

    }
}
