package com.kiligz.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValue;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanReference;
import com.kiligz.beans.factory.support.AbstractBeanDefinitionReader;
import com.kiligz.beans.factory.support.BeanDefinitionRegistry;
import com.kiligz.context.annotation.ClassPathBeanDefinitionScanner;
import com.kiligz.core.io.Resource;
import com.kiligz.core.io.ResourceLoader;
import com.kiligz.util.LogUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 读取配置在xml文件中的bean定义信息
 *
 * @author Ivan
 * @date 2022/8/22 14:20
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";

    /**
     * 使用默认的资源加载器初始化
     */
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 使用指定的资源加载器初始化
     */
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    /**
     * 通过location加载BeanDefinition，ResourceLoader选择location的加载方式
     */
    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    /**
     * 通过Resource加载BeanDefinition
     */
    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream is = resource.getInputStream()) {
            doLoadBeanDefinitions(is);
        } catch (IOException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    /**
     * 从输入流中加载BeanDefinition
     */
    private void doLoadBeanDefinitions(InputStream is) throws DocumentException {
        LogUtil.readXmlAndLoadBeanDefinition();

        SAXReader reader = new SAXReader();
        Document document = reader.read(is);

        Element root = document.getRootElement();

        // 解析component-scan标签
        Element componentScan = root.element(COMPONENT_SCAN_ELEMENT);
        if (componentScan != null) {
            String scanPath = componentScan.attributeValue(BASE_PACKAGE_ATTRIBUTE);
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }


        // 解析bean标签
        List<Element> beanList = root.elements(BEAN_ELEMENT);
        for (Element bean : beanList) {
            String id = bean.attributeValue(ID_ATTRIBUTE);
            String name = bean.attributeValue(NAME_ATTRIBUTE);
            String className = bean.attributeValue(CLASS_ATTRIBUTE);
            String initMethodName = bean.attributeValue(INIT_METHOD_ATTRIBUTE);
            String destroyMethodName = bean.attributeValue(DESTROY_METHOD_ATTRIBUTE);
            String beanScope = bean.attributeValue(SCOPE_ATTRIBUTE);

            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BeansException("Cannot find class [" + className + "]");
            }

            // id优先于name，如果id和name都为空，将类名的第一个字母转为小写后作为bean的名称
            String beanName = StrUtil.isNotEmpty(id) ? id
                    : StrUtil.isNotEmpty(name) ? name
                    : StrUtil.lowerFirst(clazz.getSimpleName());

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            beanDefinition.setBeanScope(beanScope);


            // 解析property标签
            List<Element> propertyList = bean.elements(PROPERTY_ELEMENT);
            for (Element property : propertyList) {
                String nameAttribute = property.attributeValue(NAME_ATTRIBUTE);
                String valueAttribute = property.attributeValue(VALUE_ATTRIBUTE);
                String refAttribute = property.attributeValue(REF_ATTRIBUTE);

                if (StrUtil.isEmpty(nameAttribute)) {
                    throw new BeansException("The name attribute cannot be null or empty.");
                }

                Object value = StrUtil.isNotEmpty(refAttribute) ?
                        new BeanReference(refAttribute) : valueAttribute;
                PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            // 注册BeanDefinition
            getRegistry().registerBeanDefinitionWithNoRepeated(beanName, beanDefinition);
        }
    }

    /**
     * 扫描注解Component的类，提取信息，组装成BeanDefinition
     */
    private void scanPackage(String scanPath) {
        LogUtil.scanPackage(scanPath);

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(scanPath.split(","));
    }
}
