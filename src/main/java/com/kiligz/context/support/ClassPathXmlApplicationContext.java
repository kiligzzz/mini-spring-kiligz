package com.kiligz.context.support;

/**
 * xml文件的应用上下文
 *
 * @author Ivan
 * @date 2022/9/5 16:56
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    /**
     * xml文件的位置
     */
    private final String[] configLocations;

    /**
     * 从xml文件加载BeanDefinition，并且自动刷新上下文
     */
    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    /**
     * 从xml文件加载BeanDefinition，并且自动刷新上下文
     */
    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    /**
     * 获取xml文件位置
     */
    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
