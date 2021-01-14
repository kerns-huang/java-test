package easyrule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;

import java.io.FileReader;

/**
 * @author xiaohei
 * @create 2021-01-12 下午3:34
 **/
public class EasyRuleTest {

    public static void main(String[] args) throws Exception {
        //创建一个Person实例(Fact)
        Person tom = new Person( 17,"Tom");
        Facts facts = new Facts();
        facts.put("person", tom);

        //创建规则1
        Rule ageRule = new MVELRule()
                .name("age rule")
                .description("Check if person's age is > 18 and marks the person as adult")
                .priority(1)
                .when("person.age > 18")
                .then("person.setAdult(true);")
                .when("person.age <= 18")
                .then("person.setAdult(false);");
        //创建规则2
        Rule alcoholRule = new MVELRuleFactory(new YamlRuleDefinitionReader()).
                createRule(new FileReader("/Users/apple/Documents/java-test/java-rule-test/src/main/resources/alcohol-rule.yaml"));

        Rules rules = new Rules();
        rules.register(ageRule);
        rules.register(alcoholRule);

        //创建规则执行引擎，并执行规则
        RulesEngine rulesEngine = new DefaultRulesEngine();
        System.out.println("Tom: Hi! can I have some Vodka please?");
        rulesEngine.fire(rules, facts);
        System.out.println(tom.getAdult());
    }
}
