package com.kiligz.test;


import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author Ivan
 * @date 2022/8/11 17:00
 */
public class TestSpring {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(KiligzService.class);
        beanFactory.registerBeanDefinition("kiligzService", beanDefinition);

        KiligzService kiligService = (KiligzService) beanFactory.getBean("kiligzService");
        System.out.println(kiligService);
        kiligService.helloWorld();
    }

    public static class KiligzService {
        public void helloWorld() {
            System.out.println("hello world");
        }
    }
}
