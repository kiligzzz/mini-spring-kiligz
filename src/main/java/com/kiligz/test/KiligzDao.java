package com.kiligz.test;

import java.util.HashMap;
import java.util.Map;

public class KiligzDao {
        private static final Map<String, String> map = new HashMap<String, String>() {{
           put("z:001", "zyf");
           put("z:002", "gmj");
        }};

        public String queryName(String key) {
            return map.get(key);
        }
    }