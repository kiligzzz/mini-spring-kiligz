package com.kiligz.beans.factory;

/**
 * Bean属性信息
 *
 * @author Ivan
 * @date 2022/8/16 17:22
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
