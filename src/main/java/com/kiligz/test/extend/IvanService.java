package com.kiligz.test.extend;

import com.kiligz.beans.factory.annotation.Autowired;
import com.kiligz.stereotype.Component;

/**
 * @author Ivan
 * @date 2022/9/27 18:09
 */
//@Component
public class IvanService {
    @Autowired
    private Ivan ivan;

    public Ivan getIvan() {
        return ivan;
    }
}
