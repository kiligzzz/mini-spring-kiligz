package com.kiligz.test.extend;

import com.kiligz.beans.factory.annotation.Value;
import com.kiligz.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Ivan
 * @date 2022/9/23 10:51
 */
@Component
public class Ivan {
    @Value("${name}")
    String name;

    @Value("${age}")
    Long age;

    @Value("${time}")
    LocalDateTime time;

    public String getName() {
        return name;
    }

    public Long getAge() {
        return age;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
