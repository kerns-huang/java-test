package easyrule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaohei
 * @create 2021-01-12 下午5:20
 **/
public class MapResultRuleTest {

    public static void main(String[] args) throws Exception {
        //创建一个Person实例(Fact)
        Map map=new HashMap();
        map.put("interfaceValue","2018-09-19 12:23:45");
        map.put("attrValue",null);
        Facts facts = new Facts();
        facts.put("map",map);

        //创建规则1
        Rule ageRule = new MVELRule()
                .name("age rule")
                .description("Check if person's age is > 18 and marks the person as adult")
                .priority(1)
                .when("map.get('interfaceValue') <= '2018-09-20'")
                .then("map.put('attrValue',12);");


        Rules rules = new Rules();
        rules.register(ageRule);

        //创建规则执行引擎，并执行规则
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
        System.out.println(map.get("attrValue"));
    }
}
