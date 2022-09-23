package com.kiligz.test.extend;

import com.kiligz.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ivan
 * @date 2022/9/23 10:53
 */
public class TestExtend {
    public static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-extend.xml");

    public static void main(String[] args) {
        testPlaceholder();
    }

    public static void testPlaceholder() {
        Placeholder placeholder = context.getBean("placeholder", Placeholder.class);
        System.out.println(placeholder.getName());
    }
}
