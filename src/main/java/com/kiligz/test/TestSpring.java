package com.kiligz.test;

import com.kiligz.context.ApplicationContext;
import com.kiligz.context.support.ClassPathXmlApplicationContext;

/**
 * 测试
 *
 * @author Ivan
 * @date 2022/8/11 17:00
 */
public class TestSpring {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        KiligzService kiligzService = (KiligzService) applicationContext.getBean("kiligzService");
        String name = kiligzService.queryName("001");
        System.out.println(name);
    }
}
