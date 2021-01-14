package groovy;

import com.google.common.collect.Lists;
import dto.AttrConvertRuleDTO;
import dto.ExpressDTO;
import org.junit.Test;

import javax.script.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaohei
 * @create 2021-01-14 上午10:06
 **/
public class GroovyTest {

    public static void main(String args[]) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        Compilable engine = (Compilable) manager.getEngineByName("groovy");
        CompiledScript script = engine.compile("y = x * 2; z = x * 3; return x ");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            ScriptContext scriptContext = new SimpleScriptContext();
            scriptContext.setAttribute("x", i, ScriptContext.ENGINE_SCOPE);
            Object value = script.eval(scriptContext);
            System.out.println(value);
        }
        System.err.println("groovy 消耗时间 " + (System.currentTimeMillis() - start) + "毫秒");

    }

    @Test
    public void test1() {
        AttrConvertRuleDTO attrConvertRuleDTO = new AttrConvertRuleDTO();
        List<String> interfaceCode = new ArrayList<>(1);
        interfaceCode.add("sex");
        attrConvertRuleDTO.setDynamicParams(interfaceCode);
        Map<String, Object> param = new HashMap<>(2);
        param.put("x", 2);
        ExpressDTO expressDTO = new ExpressDTO();
        expressDTO.setCondition("sex==1");
        expressDTO.setPriority(1);
        expressDTO.setActionList(Lists.newArrayList("x=3"));


    }
}
