package com.kiligz.test.extend;

import com.kiligz.beans.factory.annotation.Value;
import com.kiligz.stereotype.Component;

/**
 * @author Ivan
 * @date 2022/9/23 10:51
 */
@Component
public class Ivan {
    @Value("${name}")
    String name;

    public String getName() {
        return name;
    }
}
