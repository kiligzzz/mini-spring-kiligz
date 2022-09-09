package com.kiligz.context;

import java.util.EventObject;

/**
 * 应用事件
 *
 * @author Ivan
 * @date 2022/9/9 16:57
 */
public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }
}
