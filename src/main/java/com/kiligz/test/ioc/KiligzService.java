package com.kiligz.test.ioc;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.beans.factory.BeanFactoryAware;
import com.kiligz.beans.factory.DisposableBean;
import com.kiligz.beans.factory.InitializingBean;
import com.kiligz.context.ApplicationContext;
import com.kiligz.context.ApplicationContextAware;

public class KiligzService implements InitializingBean, DisposableBean, BeanFactoryAware, ApplicationContextAware {
    private String prefix;
    private KiligzDao kiligzDao;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public KiligzDao getKiligzDao() {
        return kiligzDao;
    }

    public void setKiligzDao(KiligzDao kiligzDao) {
        this.kiligzDao = kiligzDao;
    }

    public String queryName(String key) {
        return kiligzDao.queryName(prefix + key);
    }

    /**
     * 实现InitializingBean的初始化方法
     */
    @Override
    public void afterPropertiesSet() {
        System.out.println("--------------> [ KiligzService InitializingBean init ]");
    }

    /**
     * 实现DisposableBean的销毁方法
     */
    @Override
    public void destroy() {
        System.out.println("---> [ KiligzService DisposableBean destroy ]");
    }

    /**
     * 配置在xml中的初始化方法
     */
    public void init() {
        System.out.println("--------------> [ KiligzService xml init ]");
    }

    /**
     * 配置在xml中的销毁方法
     */
    public void xmlDestroy() {
        System.out.println("---> [ KiligzService xml destroy ]");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}