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
        kiligService.helle();
    }

    public static class KiligzService {
        String name;

        public KiligzService() {
        }

        public KiligzService(String name) {
            this.name = name;
        }

        public void helle() {
            System.out.println("hello");
        }

        public void helleName() {
            System.out.println("hello " + name);
        }
    }
}
