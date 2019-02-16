package com.liupeiqing.springframework.ioc.config.parse;

import com.liupeiqing.springframework.ioc.config.Bean;
import com.liupeiqing.springframework.ioc.config.Property;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 程序中所有的Bean之间的依赖关系我们是放在一个xml文件中进行维护的,就是applicationContext.xml　
 * ConfigManager类完成的功能是读取xml,并将所有读取到有用的信息封装到我们创建的一个Map<String,Bean>集合中,用来在初始化容器时创建bean对象.
 * @author liupeqing
 * @date 2019/2/16 10:04
 */
public class ConfigManager {


    //存放bean
    private static Map<String,Bean> map = new HashMap<>();

    //返回Map集合便于注入,key是每个Bean的name属性,value是对应的那个Bean对象
    //读取配置文件并返回读取结果
    public static Map<String,Bean> getConfig(String path){
        /**dom4j实现
         *  1.创建解析器
         *  2.加载配置文件,得到document对象
         *  3.定义xpath表达式,取出所有Bean元素
         *  4.对Bean元素继续遍历
         *      4.1将Bean元素的name/class属性封装到bean类属性中
         *      4.2获得bean下的所有property子元素
         *      4.3将属性name/value/ref分装到类Property类中
         *  5.将property对象封装到bean对象中
         *  6.将bean对象封装到Map集合中,返回map
         */
        //1.创建解析器
        SAXReader reader = new SAXReader();
        //2.加载配置文件,得到document对象
        InputStream is = ConfigManager.class.getResourceAsStream(path);
        Document doc = null;
        try{
            doc = reader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("请检查您的xml配置是否正确");
        }
        // 3.定义xpath表达式,取出所有Bean元素
        String xpath = "//bean";
        //4.对Bean元素继续遍历
        List<Element> list = doc.selectNodes(xpath);
        if (null != list && list.size() > 0){
            //4.1将Bean元素的name/class属性封装到bean类属性中

            // 4.3将属性name/value/ref分装到类Property类中
            for (Element element : list){
                Bean bean = new Bean();
                String name = element.attributeValue("name");
                String className = element.attributeValue("class");
                bean.setName(name);
                bean.setClassName(className);
                // 获取Bean元素下的所以Property子元素
                //  4.2获得bean下的所有property子元素
                List<Element> children = element.elements("property");
                if (null != children && children.size() > 0 ){
                    for (Element proEle : children){
                        Property property = new Property();
                        property.setName(proEle.attributeValue("name"));
                        property.setValue(proEle.attributeValue("value"));
                        property.setRef(proEle.attributeValue("ref"));
                        // 5.将property对象封装到bean对象中
                        bean.getProperties().add(property);

                    }
                }
                //6.将bean对象封装到Map集合中,返回map
                map.put(name,bean);
            }
        }
        return map;
    }

}
