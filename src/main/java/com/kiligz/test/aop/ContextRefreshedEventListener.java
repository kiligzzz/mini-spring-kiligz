package com.kiligz.test.aop;

import com.kiligz.context.ApplicationListener;
import com.kiligz.context.event.ContextRefreshedEvent;

/**
 * @author Ivan
 * @date 2022/9/20 16:07
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("\n======= refreshed listener : test start =======");
    }
}
