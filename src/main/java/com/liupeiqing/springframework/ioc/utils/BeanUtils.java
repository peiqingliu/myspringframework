package com.liupeiqing.springframework.ioc.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author liupeqing
 * @date 2019/2/16 11:14
 */
public class BeanUtils {

    /**
     * 为bean的属性赋值
     * @param beanObj
     * @param name
     * @return
     */
    public static Method getWriteMethod(Object beanObj,String name){
        Method method = null;
        try{
            //1、分析Bean对象，使用Introspector的getBeanInfo方法获得beanInfo
            BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());
            //2、根据BenInfo获得所以属性的描述器
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (null != propertyDescriptors && propertyDescriptors.length > 0){
                //遍历描述器
                for (PropertyDescriptor pd : propertyDescriptors){
                    String pdName = pd.getName();
                    if (pdName.equals(name)){
                        method = pd.getWriteMethod();
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (method == null){
            throw new RuntimeException("属性方法不存在");
        }

        return method;
    }
}
