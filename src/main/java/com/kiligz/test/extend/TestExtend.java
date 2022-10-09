package com.kiligz.test.extend;

import com.kiligz.context.support.ClassPathXmlApplicationContext;
import com.kiligz.core.convert.ConversionService;
import com.kiligz.core.convert.support.DefaultConversionService;

/**
 * @author Ivan
 * @date 2022/9/23 10:53
 */
public class TestExtend {
    public static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-extend.xml");

    public static void main(String[] args) {
        testConverterSpring();
    }

    /**
     * 添加到spring中的转换器
     */
    public static void testConverterSpring() {
        IvanService ivanService = context.getBean(IvanService.class);
        Ivan ivan = ivanService.getIvan();

        System.out.println(ivan.getName());
        System.out.println(ivan.getAge());
        System.out.println(ivan.getTime());
    }

    /**
     * 转换器
     */
    public static void testConverter() {
        ConversionService conversionService = new DefaultConversionService();
        System.out.println(conversionService.canConvert(String.class, Float.class));
        System.out.println(conversionService.convert("3", Float.class));
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
