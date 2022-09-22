package com.kiligz.test.aop;

import com.kiligz.context.ApplicationListener;
import com.kiligz.context.event.ContextClosedEvent;

/**
 * @author Ivan
 * @date 2022/9/20 16:09
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("======= closed listener : test stop =======\n");
    }
}
