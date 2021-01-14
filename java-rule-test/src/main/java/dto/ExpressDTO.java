package dto;

import lombok.Data;

import java.util.List;

/**
 * @author xiaohei
 * @create 2021-01-14 上午11:14
 **/
@Data
public class ExpressDTO {
    /**
     * 条件表达式
     */
    private String condition;
    /**
     * 期待的返回值
     */
    private List<String> actionList;
    /**
     * 优先级
     */
    private Integer priority;
}
