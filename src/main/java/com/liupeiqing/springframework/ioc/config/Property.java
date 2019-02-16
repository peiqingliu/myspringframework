package com.liupeiqing.springframework.ioc.config;

/**
 * 用与封装Bean子标签property内容的Property类:
 * @author liupeqing
 * @date 2019/2/16 10:01
 */
public class Property {

    private String name;

    private String value;

    private String ref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
