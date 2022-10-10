package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.DisposableBean;
import com.kiligz.beans.factory.ObjectFactory;
import com.kiligz.beans.factory.config.SingletonBeanRegistry;
import com.kiligz.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的单例bean注册方法实现
 *
 * @author Ivan
 * @date 2022/8/11 17:22
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    public DefaultSingletonBeanRegistry() {
        LogUtil.initCache(singletonObjects, earlySingletonObjects, singletonFactories);
    }

    /**
     * 一级缓存
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 二级缓存
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();
    
    /**
     * 三级缓存
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    /**
     * 拥有销毁方法的bean
     */
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 注册单例bean
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        addSingleton(beanName, singletonObject);
    }

    /**
     * 从缓存中获取bean，顺序为一级、二级、三级
     * 从三级缓存中获取时，将获取到的bean从三级缓存中删除并放入二级缓存中
     */
    @Override
    public Object getSingleton(String beanName) {
        // 从一级缓存中获取
        Object bean = singletonObjects.get(beanName);
        if (bean != null) {
            LogUtil.getFromFirst();

            return bean;
        }

        // 从二级缓存中获取
        bean = earlySingletonObjects.get(beanName);
        if (bean != null) {
            LogUtil.getFromSecond();

            return bean;
        }

        // 从三级缓存中获取
        ObjectFactory<?> objectFactory = singletonFactories.get(beanName);
        if (objectFactory != null) {
            bean = objectFactory.getObject();
            // 从三级缓存放进二级缓存
            earlySingletonObjects.put(beanName, bean);
            singletonFactories.remove(beanName);

            LogUtil.getFromThird();
        }
        return bean;
    }

    /**
     * 添加bean到一级缓存，同时删除二级、三级缓存中对应的临时存储
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);

        LogUtil.putInFirst();
    }
    
    /**
     * 一级缓存中没有，则添加到三级缓存，临时存储刚创建完的bean，延迟触发被引用的bean获取当前bean的逻辑
     * 同时从二级缓存中删除
     */
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if (!singletonObjects.containsKey(beanName)) {
            singletonFactories.put(beanName, singletonFactory);
            earlySingletonObjects.remove(beanName);

            LogUtil.putInThird();
        }
    }

    /**
     * 注册有销毁方法的bean
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        LogUtil.registerDisposableBean();

        disposableBeans.put(beanName, bean);
    }

    /**
     * 销毁所有拥有销毁方法的bean
     */
    public void destroySingletons() {
        for (String beanName : disposableBeans.keySet()) {
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
