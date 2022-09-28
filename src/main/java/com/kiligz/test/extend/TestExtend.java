package com.kiligz.test.extend;

import com.kiligz.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ivan
 * @date 2022/9/23 10:53
 */
public class TestExtend {
    public static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-extend.xml");

    public static void main(String[] args) {
        testAutowired();
    }

    /**
     * 自动注入
     */
    public static void testAutowired() {
        IvanService ivanService = context.getBean(IvanService.class);
        Ivan ivan = ivanService.getIvan();
        System.out.println(ivan.getName());
    }

    /**
     * xml、@Value占位符
     */
    public static void testPlaceholder() {
        Ivan ivan = context.getBean(Ivan.class);
        System.out.println(ivan.getName());
    }
}
