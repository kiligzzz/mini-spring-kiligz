package com.kiligz.test.aop;

import com.kiligz.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ivan
 * @date 2022/9/14 10:02
 */
public class TestAop {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-aop.xml");
        applicationContext.registerShutdownHook();
        // aopService没放进singletonObjects
        // 修复：将DefaultAdvisorAutoProxyCreator（自动代理）中的
        // 织入逻辑从postProcessBeforeInstantiation移到postProcessAfterInitialization
        AopService aopService = applicationContext.getBean("aopService", AopService.class);
        aopService.printAop();
    }
}
