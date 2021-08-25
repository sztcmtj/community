package com.nanxiaoqiao.community;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

public class FastJsonTests {
    @Test
    public void testJsonObject() {
        Person p1 = new Person("Adam", 23, "male", "");
        JSONObject jo1 = JSONObject.parseObject(JSONObject.toJSONString(p1));
        Person p2 = new Person("Bob", 45, "female", "");
        Ext ext1 = new Ext("extInfo1", "extInfo2", p2);
        JSONObject jo2 = JSONObject.parseObject(JSONObject.toJSONString(ext1));
        jo1.put("extInfo", jo2.toJSONString());
        JSONObject extInfo = jo1.getJSONObject("extInfo");
        System.out.println(extInfo.toJSONString());
        System.out.println(jo1.toJSONString());
    }
}

@Data
@AllArgsConstructor
class Person {
    private String name;
    private Integer age;
    private String gender;
    private String extInfo;
}

@Data
@AllArgsConstructor
class Ext {
    private String extInfo1;
    private String extInfo2;
    private Person person;
}
