package com.kiligz.util;

import com.kiligz.beans.factory.ObjectFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 日志工具类
 *
 * @author Ivan
 * @date 2022/10/10 11:32
 */
public class LogUtil {
    /**
     * 容器刷新
     */
    public static void refresh() {
        System.out.println("[ refresh ] ");
    }

    /**
     * 容器关闭
     */
    public static void close() {
        System.out.println("[ close ]");
    }





    /**
     * 创建beanFactory
     */
    public static void createBeanFactory() {
        System.out.println("---> [ create defaultListableBeanFactory ] ");
    }
    
    /**
     * 读取xml配置文件，加载BeanDefinition
     */
    public static void readXmlAndLoadBeanDefinition() {
        System.out.println("---> [ read xml && load beanDefinitions ] ");
    }

    /**
     * 注册应用容器感知处理器，让实现ApplicationContextAware接口的类可以感知所属的ApplicationContext
     */
    public static void registerApplicationContextAwareProcessor() {
        System.out.println("---> [ register ApplicationContextAware processor ] ");
    }

    /**
     * 在bean实例化之前，执行BeanFactoryPostProcessor
     */
    public static void invokeBeanFactoryPostProcessor() {
        System.out.println("---> [ invoke beanFactoryPostProcessor ] ");
    }
    
    /**
     * 注册BeanPostProcessors
     */
    public static void registerBeanPostProcessors() {
        System.out.println("---> [ register beanPostProcessors ] ");
    }
    
    /**
     * 初始化应用事件广播器
     */
    public static void initApplicationEventMulticaster() {
        System.out.println("---> [ init ApplicationEventMulticaster ] ");
    }

    /**
     * 注册应用监听器
     */
    public static void registerApplicationListeners() {
        System.out.println("---> [ register ApplicationListeners ] ");
    }

    /**
     * 预先实例化bean
     */
    public static void preInstantiateSingletons() {
        System.out.println("---> [ pre instantiate Singletons ] ");
    }

    /**
     * 发布容器刷新事件
     */
    public static void publishEvent(String eventName) {
        System.out.printf("---> [ publish %s ] %n", eventName);
    }

    /**
     * 注册类型转换服务
     */
    public static void registerConversionService() {
        System.out.println("---> [ register conversionService ] ");
    }





    /**
     * 扫描包
     */
    public static void scanPackage(String scanPath) {
        System.out.printf("-------> [ scan package: %s ] %n", scanPath);
    }

    /**
     * 获取bean
     */
    public static void getBean(String beanName) {
        System.out.printf("-------> [ get bean: %s ] %n", beanName);
    }

    /**
     * 替换xml配置文件占位符（对BeanDefinition操作）,添加value解析器
     */
    public static void replaceXmlPlaceHolderAndAddValueResolver() {
        System.out.println("-------> [ replace xml placeholder & add valueResolver (resolve @Value placeholder) ] ");
    }

    /**
     * 依赖bean
     */
    public static void refBean(String beanName) {
        System.out.printf("-------> [ ref bean: %s ] %n", beanName);
    }

    /**
     * jdk自动代理
     */
    public static void jdkAop(String beanName) {
        System.out.printf("-------> [ jdk aop %s ] %n", beanName);
    }

    /**
     * cglib自动代理
     */
    public static void cglibAop(String beanName) {
        System.out.printf("-------> [ cglib aop %s ] %n", beanName);
    }





    /**
     * 注册自动装配注解处理器
     */
    public static void registerAutowiredAnnotationProcessor() {
        System.out.println("----------> [ register AutowiredAnnotation processor ] ");
    }

    /**
     * 创建bean
     */
    public static void createBean(String beanName) {
        System.out.printf("----------> [ create bean: %s ] %n", beanName);
    }

    /**
     * FactoryBean获取其生产的object作为实际bean
     */
    public static void factoryBeanGetObject() {
        System.out.println("----------> [ FactoryBean get object ] ");
    }

    /**
     * cglib策略实例化bean
     */
    public static void cglibInstantiateBean(String beanName) {
        System.out.printf("--------------> [ cglib instantiate %s ] %n", beanName);
    }

    /**
     * 为bean填充属性
     */
    public static void applyPropertyValues() {
        System.out.println("--------------> [ apply PropertyValues ] ");
    }

    /**
     * 初始化bean
     */
    public static void initializeBean(String beanName) {
        System.out.printf("--------------> [ initialize %s ] %n", beanName);
    }

    /**
     * 加工PropertyValues，@Value、@Autowired赋值
     */
    public static void processPropertyValues() {
        System.out.println("--------------> [ process PropertyValues ] ");
    }

    /**
     * 注册有销毁方法的bean
     */
    public static void registerDisposableBean() {
        System.out.println("--------------> [ register DisposableBean ]");
    }

    /**
     * 放入三级缓存
     */
    public static void putInThird() {
        System.out.println("--------------> [ put in ThirdCache ] ");
        logCache("", "remove", "put");
    }

    /**
     * 从三级缓存中获取
     */
    public static void getFromThird() {
        System.out.println("--------------> [ get from ThirdCache ] ");
        logCache("", "put", "remove");
    }

    /**
     * 获取早期的bean引用
     */
    public static void getEarlyBeanReference(String beanName) {
        System.out.printf("--------------> [ get early beanReference: %s ] %n", beanName);
    }

    /**
     * 放入一级缓存
     */
    public static void putInFirst() {
        System.out.println("--------------> [ put in FirstCache ]");
        logCache("put", "remove", "remove");
    }

    /**
     * 从二级缓存中获取
     */
    public static void getFromSecond() {
        System.out.println("--------------> [ get from SecondCache ]");
    }

    /**
     * 从一级缓存中获取
     */
    public static void getFromFirst() {
        System.out.println("--------------> [ get from FirstCache ]");
    }






    /**
     * 处理@Value注解
     */
    public static void processValueAnnotation() {
        System.out.println("------------------> [ process @Value annotation ]");
    }

    /**
     * 处理@Autowired注解
     */
    public static void processAutowiredAnnotation() {
        System.out.println("------------------> [ process @Autowired annotation ]");
    }

    /**
     * 初始化之后执行postProcess
     */
    public static void postProcessAfterInitialization() {
        System.out.println("------------------> [ postProcess after initialization (autoProxy) ]");
    }





    /**
     * 执行初始化方法
     */
    public static void invokeInitMethods() {
        System.out.println("------------------> [ invoke initMethods ] ");
    }

    /**
     * 为实现BeanFactoryAware接口的设置beanFactory
     */
    public static void setBeanFactory() {
        System.out.println("------------------> [ set beanFactory ]");
    }

    /**
     * 为实现ApplicationContextAware接口的设置ApplicationContext
     */
    public static void setApplicationContext() {
        System.out.println("------------------> [ set applicationContext ]");
    }
    
    
    
    /**
     * 注册类型转换服务和类型转换器
     */
    public static void registerConversionServiceAndConverters() {
        System.out.println("----------------------> [ register conversionService & converters ]");
    }







    private static Set<String> firstCache;
    private static Set<String> secondCache;
    private static Set<String> thirdCache;
    public static final List<String> beanNameList = Arrays.asList("a", "b");

    public static void initCache(Map<String, Object> firstCache,
                                 Map<String, Object> secondCache,
                                 Map<String, ObjectFactory<?>> thirdCache) {
        LogUtil.firstCache = firstCache.keySet();
        LogUtil.secondCache = secondCache.keySet();
        LogUtil.thirdCache = thirdCache.keySet();
    }

    private static void logCache(String first, String second, String third) {
        if (firstCache.stream().anyMatch(beanNameList::contains)
                || secondCache.stream().anyMatch(beanNameList::contains)
                || thirdCache.stream().anyMatch(beanNameList::contains)) {
            Set<String> firstSet = firstCache.stream().filter(beanNameList::contains).collect(Collectors.toSet());
            Set<String> secondSet = secondCache.stream().filter(beanNameList::contains).collect(Collectors.toSet());
            Set<String> thirdSet = thirdCache.stream().filter(beanNameList::contains).collect(Collectors.toSet());
            System.out.printf("==================> FirstCache[%s]:%s | SecondCache[%s]:%s | ThirdCache[%s]:%s %n",
                    first, firstSet, second, secondSet, third, thirdSet);
        }
    }
}
