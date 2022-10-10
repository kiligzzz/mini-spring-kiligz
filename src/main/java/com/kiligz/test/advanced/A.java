package com.kiligz.test.advanced;

import com.kiligz.beans.factory.annotation.Autowired;
import com.kiligz.stereotype.Component;

/**
 * @author Ivan
 * @date 2022/10/9 16:34
 */
@Component
public class A {
    @Autowired
    B b;

    public B getB() {
        return b;
    }

    public void print() {
        System.out.println("=== AAA ===");
    }
}
