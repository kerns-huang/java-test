package easyrule;

import lombok.Data;
import org.jeasy.rules.api.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohei
 * @create 2021-01-12 下午5:03
 **/
@Data
public class RuleDTO {

    private String name = Rule.DEFAULT_NAME;
    private String description = Rule.DEFAULT_DESCRIPTION;
    private int priority = Rule.DEFAULT_PRIORITY;
    private String condition;
    private List<String> actions = new ArrayList<>();
}
