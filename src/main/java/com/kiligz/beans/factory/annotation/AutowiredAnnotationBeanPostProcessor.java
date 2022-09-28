package com.kiligz.beans.factory.annotation;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValue;
import com.kiligz.beans.PropertyValues;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.beans.factory.BeanFactoryAware;
import com.kiligz.beans.factory.ConfigurableListableBeanFactory;
import com.kiligz.beans.factory.config.BeanReference;
import com.kiligz.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.kiligz.util.ClassUtil;

import java.lang.reflect.Field;

/**
 * 自动装配注解BeanPostProcessor
 *
 * 在包扫描时添加
 * @see com.kiligz.context.annotation.ClassPathBeanDefinitionScanner#doScan(String...)
 *
 * @author Ivan
 * @date 2022/9/26 17:37
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.println("--------------> [ process PropertyValues ]");
        Class<?> clazz = ClassUtil.getOriginClass(bean.getClass());
        Field[] fields = clazz.getDeclaredFields();

        // 处理@Value注解
        for (Field field : fields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation == null) continue;

            System.out.println("------------------> [ process @Value annotation ]");
            String value = beanFactory.resolveEmbeddedValue(valueAnnotation.value());
            pvs.addPropertyValue(new PropertyValue(field.getName(), value));
        }

        // 处理@Autowired注解，没有Qualifier则按Class类型注入，若有则按指定name注入
        for (Field field : fields) {
            Autowired autowired = field.getAnnotation(Autowired.class);

            if (autowired != null) {
                System.out.println("------------------> [ process @Autowired annotation ]");
                Qualifier qualifier = field.getAnnotation(Qualifier.class);

                BeanReference beanReference = qualifier == null ?
                        new BeanReference(field.getType()) :
                        new BeanReference(qualifier.value());

                PropertyValue pv = new PropertyValue(field.getName(), beanReference);
                pvs.addPropertyValue(pv);
            }
        }
        return pvs;
    }
}
