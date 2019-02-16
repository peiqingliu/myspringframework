package com.liupeiqing.springframework;

/**
 *
 *
 * 　程序中所有的Bean之间的依赖关系我们是放在一个xml文件中进行维护的,就是applicationContext.xml　　
 * 　ConfigManager类完成的功能是读取xml,并将所有读取到有用的信息封装到我们创建的一个Map<String,Bean>集合中,用来在初始化容器时创建bean对象.
 * 　定义一个BeanFactory的接口,接口中有一个getBean(String name)方法,用来返回你想要创建的那个对象.
 * 　然后定义一个该接口的实现类ClassPathXmlApplicationContext.就是在这个类的构造方法中,初始化容器,
 *   通过调用ConfigManager的方法返回的Map集合,通过反射和内省一一创建bean对象.这里需要注意,对象的创建有两个时间点,这取决与bean标签中scope属性的值:
 * 　如果scope="singleton",那么对象在容器初始化时就已创建好,用的时候只需要去容器中取即可.
 * 　如果scope="prototype",那么容器中不保存这个bean的实例对象,每次开发者需要使用这个对象时再进行创建.
 *
 *
 * @author liupeqing
 * @date 2019/2/16 9:57
 */
public class AppApplication {
}
