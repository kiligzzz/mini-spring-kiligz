package com.kiligz.test.extend;

import com.kiligz.beans.factory.FactoryBean;
import com.kiligz.core.convert.converter.Converter;
import com.kiligz.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义添加类型转换器
 *
 * @author Ivan
 * @date 2022/10/9 11:22
 */
@Component("converters")
public class ConverterFactoryBean implements FactoryBean<Set<?>> {
    @Override
    public Set<?> getObject() throws Exception {
        Set<Object> set = new HashSet<>();
        set.add(new StringToLocalDateTime());
        return set;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private static class StringToLocalDateTime implements Converter<String, LocalDateTime> {
        private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public LocalDateTime convert(String source) {
            return LocalDateTime.parse(source, dtf);
        }
    }
}
