package com.kiligz.core.convert.converter;

import java.util.Objects;
import java.util.Set;

/**
 * 通用转换器接口
 *
 * @author Ivan
 * @date 2022/9/28 15:27
 */
public interface GenericConverter {
    /**
     * 获取可转换的类型
     */
    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 转换对象
     */
    Object convert(Object source, Class<?> sourceType, Class<?> targetType);

    class ConvertiblePair {
        private final Class<?> sourceType;
        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConvertiblePair that = (ConvertiblePair) o;
            return Objects.equals(sourceType, that.sourceType) && Objects.equals(targetType, that.targetType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sourceType, targetType);
        }
    }
}
