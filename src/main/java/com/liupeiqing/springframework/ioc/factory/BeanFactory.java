package com.liupeiqing.springframework.ioc.factory;

/**
 * BeanFactory的接口,接口中有一个getBean(String name)方法,用来返回你想要创建的那个对象.
 * @author liupeqing
 * @date 2019/2/16 10:37
 */
public interface BeanFactory {
    //核心方法，获得bean
    Object getBean(String beanName);
}
