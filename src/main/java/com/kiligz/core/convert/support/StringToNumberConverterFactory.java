package com.kiligz.core.convert.support;

import com.kiligz.core.convert.converter.Converter;
import com.kiligz.core.convert.converter.ConverterFactory;

/**
 * String转Number的转换器工厂
 *
 * @author Ivan
 * @date 2022/9/28 15:35
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {
    /**
     * 获取转换器
     */
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    /**
     * String转Number转换器
     */
    private static final class StringToNumber<T extends Number> implements Converter<String, T> {
        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (source.length() == 0)
                return null;

            try {
                if (Number.class.isAssignableFrom(targetType)) {
                    return targetType.getDeclaredConstructor(String.class).newInstance(source);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Cannot convert String [" + source + "] to target class [" + targetType.getSimpleName() + "]");
            }
        }
    }
}
