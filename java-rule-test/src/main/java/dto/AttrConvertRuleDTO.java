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
     * 关联的接口返回值做为动态入参
     */
    private List<String> interfaceReturnParam;
    /**
     * 常规如餐，直接写死
     */
    private Map<String, Object> params;

    private List<ExpressDTO> express;


}
