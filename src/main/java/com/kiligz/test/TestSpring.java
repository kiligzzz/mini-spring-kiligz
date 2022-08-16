package com.kiligz.test;


import com.kiligz.beans.factory.PropertyValue;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanReference;
import com.kiligz.beans.factory.support.DefaultListableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan
 * @date 2022/8/11 17:00
 */
public class TestSpring {

    public static void main(String[] args) {
        // 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册KiligzDao
        beanFactory.registerBeanDefinition("kiligzDao", new BeanDefinition(KiligzDao.class));

        // 设置KiligzService属性
        List<PropertyValue> propertyValueList = new ArrayList<>();
        PropertyValue prefix = new PropertyValue("prefix", "z:");
        PropertyValue kiligzDao = new PropertyValue("kiligzDao", new BeanReference("kiligzDao"));
        propertyValueList.add(prefix);
        propertyValueList.add(kiligzDao);

        // 注册KiligzService
        BeanDefinition beanDefinition = new BeanDefinition(KiligzService.class, propertyValueList);
        beanFactory.registerBeanDefinition("kiligzService", beanDefinition);

        // 获取KiligzService
        KiligzService kiligService = (KiligzService) beanFactory.getBean("kiligzService");

        // 调用方法测试
        System.out.println(kiligService.queryName("002"));
    }

    public static class KiligzService {
        String prefix;
        KiligzDao kiligzDao;

        public String queryName(String key) {
            return kiligzDao.queryName(prefix + key);
        }
    }

    public static class KiligzDao {
        private static final Map<String, String> map = new HashMap<String, String>() {{
           put("z:001", "zyf");
           put("z:002", "gmj");
        }};

        public String queryName(String key) {
            return map.get(key);
        }
    }
}
