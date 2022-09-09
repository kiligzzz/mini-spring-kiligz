package com.kiligz.context.support;

import cn.hutool.core.util.StrUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.ConfigurableListableBeanFactory;
import com.kiligz.beans.factory.config.BeanFactoryPostProcessor;
import com.kiligz.beans.factory.config.BeanPostProcessor;
import com.kiligz.context.ApplicationEvent;
import com.kiligz.context.ApplicationListener;
import com.kiligz.context.ConfigurableApplicationContext;
import com.kiligz.context.event.ApplicationEventMulticaster;
import com.kiligz.context.event.ContextClosedEvent;
import com.kiligz.context.event.ContextRefreshedEvent;
import com.kiligz.context.event.SimpleApplicationEventMulticaster;
import com.kiligz.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象的应用上下文
 *
 * @author Ivan
 * @date 2022/9/5 16:51
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    /**
     * 应用事件广播器
     */
    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * 刷新容器
     */
    @Override
    public void refresh() throws BeansException {
        System.out.println("[ refresh ] ");

        // 创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //添加ApplicationContextAwareProcessor，让继承自ApplicationContextAware的bean能感知所属ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor需要提前于其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        // 初始化应用事件广播器
        initApplicationEventMulticaster();

        // 注册事件监听器
        registerListeners();

        // 提前实例化单例bean
        beanFactory.preInstantiateSingletons();

        // 发布容器刷新完成事件
        finishRefresh();
    }

    /**
     * 向虚拟机中注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     */
    @Override
    public void registerShutdownHook() {
        Thread shutdownHook = new Thread(this::doClose);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    /**
     * 创建BeanFactory，并加载BeanDefinition
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取BeanFactory
     */
    public abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 在bean实例化之前，执行BeanFactoryPostProcessor
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("---> [ invoke beanFactoryPostProcessor ] ");

        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册BeanPostProcessor
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("---> [ register beanPostProcessors ] ");

        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 初始化应用事件广播器
     */
    private void initApplicationEventMulticaster() {
        System.out.println("---> [ init ApplicationEventMulticaster ] ");

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);

        String beanName = StrUtil.lowerFirst(applicationEventMulticaster.getClass().getSimpleName());
        beanFactory.registerSingleton(beanName, applicationEventMulticaster);
    }

    /**
     * 注册事件监听器
     */
    @SuppressWarnings("all")
    protected void registerListeners() {
        System.out.println("---> [ register ApplicationListeners ] ");

        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    /**
     * 发布容器刷新完成事件
     */
    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    /**
     * 发布事件
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        System.out.printf("---> [ publish %s ]%n", event.getClass().getSimpleName());

        applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * 返回指定类型的所有bean实例
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    /**
     * 返回定义的所有bean的名称
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    /**
     * 获取bean
     */
    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    /**
     * 获取bean
     */
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    /**
     * 关闭应用上下文
     */
    @Override
    public void close() {
        doClose();
    }

    /**
     * 关闭应用上下文的实现
     */
    protected void doClose() {
        System.out.println("[ do close ]");
        //发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));
        destroyBeans();
    }

    /**
     * 销毁所有拥有销毁方法的bean
     */
    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }
}
