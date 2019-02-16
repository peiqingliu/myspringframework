package com.liupeiqing.springframework.ioc.test;

import com.liupeiqing.springframework.ioc.bean.A;
import com.liupeiqing.springframework.ioc.bean.B;
import com.liupeiqing.springframework.ioc.config.Bean;
import com.liupeiqing.springframework.ioc.config.parse.ConfigManager;
import com.liupeiqing.springframework.ioc.factory.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.util.Map;

/**
 * @author liupeqing
 * @date 2019/2/16 12:10
 */
public class MyTest {

    @Test
    public void test1() {
        Map<String, Bean> config = ConfigManager.getConfig("/applicationContext.xml");
        System.out.print(config.toString());
    }

    @Test
    public void test2(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
        A a1 = (A) context.getBean("a");
        System.out.println(a1);
    }

    @Test
    public void test3() {

        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");

        B b = (B) classPathXmlApplicationContext.getBean("b");

        System.out.print(b.getA().getName());
    }
}
