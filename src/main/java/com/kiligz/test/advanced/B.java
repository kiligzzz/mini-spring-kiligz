package com.kiligz.test.advanced;

import com.kiligz.beans.factory.annotation.Autowired;
import com.kiligz.stereotype.Component;

/**
 * @author Ivan
 * @date 2022/10/9 16:33
 */
@Component
public class B {
    @Autowired
    A a;

    public A getA() {
        return a;
    }

    public void print() {
        System.out.println("=== BBB ===");
    }
}
