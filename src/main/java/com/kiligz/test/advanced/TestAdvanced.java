package com.kiligz.test.advanced;

import com.kiligz.context.support.ClassPathXmlApplicationContext;
import com.kiligz.util.LogUtil;

/**
 * @author Ivan
 * @date 2022/10/9 16:16
 */
public class TestAdvanced {
    public static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-advanced.xml");

    public static void main(String[] args) {
        testCircularDependencies();
    }

    public static void testCircularDependencies() {
        A a = context.getBean(A.class);
        B b = context.getBean(B.class);
        a.print();
        b.print();
        System.out.println(a.getB() == b);
    }
}
