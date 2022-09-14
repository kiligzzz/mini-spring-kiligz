package com.kiligz.test.ioc;

import com.kiligz.beans.factory.FactoryBean;

/**
 * @author Ivan
 * @date 2022/9/9 10:26
 */
public class KiligzFactoryBean implements FactoryBean<KiligzService> {

    private String prefix;

    @Override
    public KiligzService getObject() throws Exception {
        KiligzService kiligzService = new KiligzService();
        kiligzService.setPrefix(prefix);
        return kiligzService;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
