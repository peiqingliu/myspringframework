package com.liupeiqing.springframework.ioc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于封装Bean标签信息的Bean类
 * @author liupeqing
 * @date 2019/2/16 9:58
 */
public class Bean {

    //表示bean的名称
    private String name;
    //表示bean所在的类
    private String className;
    //单例
    private String scope = "singleton";

    //表示bean的 子属性 Property
    private List<Property> properties = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
