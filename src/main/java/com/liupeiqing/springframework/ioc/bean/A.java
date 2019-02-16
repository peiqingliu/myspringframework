package com.liupeiqing.springframework.ioc.bean;

/**
 * @author liupeqing
 * @date 2019/2/16 11:58
 */
public class A {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "A{" +
                "name='" + name + '\'' +
                '}';
    }
}
