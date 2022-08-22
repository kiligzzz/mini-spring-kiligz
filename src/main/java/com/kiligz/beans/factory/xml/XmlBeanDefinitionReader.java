package com.kiligz.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValue;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanReference;
import com.kiligz.beans.factory.support.AbstractBeanDefinitionReader;
import com.kiligz.beans.factory.support.BeanDefinitionRegistry;
import com.kiligz.core.io.Resource;
import com.kiligz.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

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
        } catch (IOException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    /**
     * 从输入流中加载BeanDefinition
     */
    private void doLoadBeanDefinitions(InputStream is) {
        Document document = XmlUtil.readXML(is);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element) {
                if (BEAN_ELEMENT.equals((childNodes.item(i)).getNodeName())) {
                    // 解析bean标签
                    Element bean = (Element) childNodes.item(i);
                    String id = bean.getAttribute(ID_ATTRIBUTE);
                    String name = bean.getAttribute(NAME_ATTRIBUTE);
                    String className = bean.getAttribute(CLASS_ATTRIBUTE);

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
                    
                    NodeList beanChildNodes = bean.getChildNodes();
                    for (int j = 0; j < beanChildNodes.getLength(); j++) {
                        if (beanChildNodes.item(j) instanceof Element) {
                            if (PROPERTY_ELEMENT.equals((beanChildNodes.item(j)).getNodeName())) {
                                //解析property标签
                                Element property = (Element) beanChildNodes.item(j);
                                String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                                String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                                String refAttribute = property.getAttribute(REF_ATTRIBUTE);

                                if (StrUtil.isEmpty(nameAttribute)) {
                                    throw new BeansException("The name attribute cannot be null or empty.");
                                }

                                Object value = StrUtil.isNotEmpty(refAttribute) ?
                                        new BeanReference(refAttribute) : valueAttribute;
                                PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                            }
                        }
                    }
                    if (getRegistry().containsBeanDefinition(beanName)) {
                        // beanName不能重名
                        throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed.");
                    }
                    // 注册BeanDefinition
                    getRegistry().registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }
    }
}
