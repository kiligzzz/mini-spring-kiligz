package com.kiligz.test;

import com.kiligz.beans.factory.support.BeanDefinitionReader;
import com.kiligz.beans.factory.support.DefaultListableBeanFactory;
import com.kiligz.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 测试
 *
 * @author Ivan
 * @date 2022/8/11 17:00
 */
public class TestSpring {

    public static void main(String[] args) {
        // 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 初始化BeanDefinitionReader
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        // 从xml中加载BeanDefinition
        beanDefinitionReader.loadBeanDefinitions("classpath:spring/spring.xml");

        // 获取KiligzService
        KiligzService kiligService = (KiligzService) beanFactory.getBean("kiligzService");

        // 调用方法测试
        System.out.println(kiligService.queryName("001"));
    }
}
