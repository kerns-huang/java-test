package easyrule;

import lombok.Data;

/**
 * @author xiaohei
 * @create 2021-01-12 下午5:07
 **/
@Data
public class Person {
   private Integer age;

    private String name;

    private Boolean adult;

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }
}
