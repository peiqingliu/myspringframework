package com.liupeiqing.springframework.ioc.factory;

import com.liupeiqing.springframework.ioc.config.Bean;
import com.liupeiqing.springframework.ioc.config.Property;
import com.liupeiqing.springframework.ioc.config.parse.ConfigManager;
import com.liupeiqing.springframework.ioc.utils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 然后定义一个Beanfactory的实现类ClassPathXmlApplicationContext.就是在这个类的构造方法中,初始化容器,
 * 通过调用ConfigManager的方法返回的Map集合,通过反射和内省一一创建bean对象.这里需要注意,对象的创建有两个时间点,这取决与bean标签中scope属性的值:
 * 　如果scope="singleton",那么对象在容器初始化时就已创建好,用的时候只需要去容器中取即可.
 * 　如果scope="prototype",那么容器中不保存这个bean的实例对象,每次开发者需要使用这个对象时再进行创建.
 * @author liupeqing
 * @date 2019/2/16 10:41
 */
public class ClassPathXmlApplicationContext implements BeanFactory{

    // 获得读取的配置文件中的Map信息
    private Map<String,Bean> map;
    /**
     *
     * 模拟Spring的容器 spring容器本质上就是一个map
     * 作为IOC容器使用,放置sring放置的对象
     */
    private Map<String,Object> context = new  HashMap<String,Object>();

    public ClassPathXmlApplicationContext(String path){
        // 1.读取配置文件得到需要初始化的Bean信息
        map = ConfigManager.getConfig(path);
        if (null != map){
            // 2.遍历配置,初始化Bean
            for (Map.Entry<String,Bean> en : map.entrySet()){
                String beanName = en.getKey();
                Bean bean = en.getValue();
                Object existBean = context.get(beanName);
                // 当容器中为空并且bean的scope属性为singleton时
                if (null == existBean && bean.getScope().equals("singleton")){
                    // 根据字符串创建Bean对象
                    Object benaObj = createBean(bean);
                    context.put(beanName,benaObj);
                }

            }
        }
    }

    /**
     * 通过反射创建bean
     * @param bean
     * @return
     */
    private Object createBean(Bean bean){
        //创建该类对象
        Class clazz = null;
        Object beanObj = null;
        String className = bean.getClassName();
        try{
            // 1、获得要创建的Class
            clazz = Class.forName(className);
            //2、获得Class后使用class创建对象
            beanObj = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("没有找到该类" + bean.getClassName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("没有提供无参构造器");
        }

        //3. 获得bean的属性,将其注入
        Object invoke = null;
        if (bean.getProperties().size() > 0 && bean.getProperties() != null){
            for (Property property: bean.getProperties()){
                // 注入分两种情况
                // 获得要注入的属性名称
                String proName = property.getName();
                String ref = property.getRef();
                Method method = null;
                try{
                    method = BeanUtils.getWriteMethod(beanObj,proName);
                }catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("您的bean没有set方法" + className);
                }
                Object result = null;
                if (null != property.getValue()){
                    //value不等于空，表示是普通属性
                    //获取要注入的实际值
                    String value = property.getValue();
                    result = value;
                }else if (null != property.getRef()){
                    //ref不等于空，表示是bean注入
                    // 先从容器中判断当前要注入的bean是否已经创建了
                    // 看一看当前IOC容器中是否已存在该bean,有的话直接设置没有的话使用递归,创建该bean对象
                    Object existBean = context.get(property.getRef());
                    if (existBean == null){
                        // 递归的创建一个bean
                        existBean = createBean(map.get(property.getRef()));
                        // 放置到context容器中
                        // 只有当scope="singleton"时才往容器中放
                        if (map.get(property.getRef()).getScope().equals("singleton")){
                            context.put(property.getRef(),existBean);
                        }
                    }
                    result = existBean;
                }
                try {
                    method.invoke(beanObj, result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
        return beanObj;
    }


    @Override
    public Object getBean(String beanName) {
        return context.get(beanName);
    }
}
