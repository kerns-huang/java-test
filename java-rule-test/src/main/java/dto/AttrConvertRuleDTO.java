package dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author xiaohei
 * @create 2021-01-14 上午11:10
 **/
@Data
public class AttrConvertRuleDTO {
    /**
     * 动态字段，关联的接口或者关联表里面的某个字段，根据这个字段来做规则转换
     */
    private List<String> dynamicParams;
    /**
     * 常规如餐，直接写死
     */
    private Map<String, Object> params;

    private List<ExpressDTO> express;


}
