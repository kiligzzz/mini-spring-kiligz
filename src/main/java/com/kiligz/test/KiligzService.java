package com.kiligz.test;

import com.kiligz.beans.factory.DisposableBean;
import com.kiligz.beans.factory.InitializingBean;

public class KiligzService implements InitializingBean, DisposableBean {
    String prefix;
    KiligzDao kiligzDao;

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
}