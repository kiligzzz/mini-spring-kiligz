package com.kiligz.test.extend;

import com.kiligz.stereotype.Component;

/**
 * @author Ivan
 * @date 2022/9/23 10:51
 */
@Component
public class Placeholder {
    String name = "ivan";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
