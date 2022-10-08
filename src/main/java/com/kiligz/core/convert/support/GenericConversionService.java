package com.kiligz.core.convert.support;

import com.kiligz.core.convert.ConversionService;
import com.kiligz.core.convert.converter.Converter;
import com.kiligz.core.convert.converter.ConverterFactory;
import com.kiligz.core.convert.converter.ConverterRegistry;
import com.kiligz.core.convert.converter.GenericConverter;
import com.kiligz.core.convert.converter.GenericConverter.ConvertiblePair;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 通用的类型转换服务
 *
 * @author Ivan
 * @date 2022/9/28 15:32
 */
@SuppressWarnings("unchecked")
public class GenericConversionService implements ConversionService, ConverterRegistry {
    /**
     * 转换器map
     */
    private final Map<ConvertiblePair, GenericConverter> converterMap = new HashMap<>();

    /**
     * 能否转换
     */
    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        GenericConverter converter = getConverter(sourceType, targetType);
        return converter != null;
    }

    /**
     * 转换
     */
    @Override
    public <S, T> T convert(S source, Class<T> targetType) {
        Class<?> sourceType = source.getClass();
        GenericConverter converter = getConverter(sourceType, targetType);
        return (T) converter.convert(source, sourceType, targetType);
    }

    /**
     * 添加转换器
     */
    @Override
    public void addConverter(Converter<?, ?> converter) {
        ConvertiblePair typeInfo = getRequiredTypeInfo(converter);
        addConverterMap(new ConverterAdapter(typeInfo, converter));
    }

    /**
     * 添加转换器工厂
     */
    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        ConvertiblePair typeInfo = getRequiredTypeInfo(converterFactory);
        addConverterMap(new ConverterFactoryAdapter(typeInfo, converterFactory));
    }

    /**
     * 添加通用转换器
     */
    @Override
    public void addConverter(GenericConverter converter) {
        addConverterMap(converter);
    }

    /**
     * 将转换器添加到map中
     */
    private void addConverterMap(GenericConverter converter) {
        for (ConvertiblePair convertibleType : converter.getConvertibleTypes()) {
            converterMap.put(convertibleType, converter);
        }
    }

    /**
     * 获取转换器
     */
    protected <S, T> GenericConverter getConverter(Class<S> sourceType, Class<T> targetType) {
        List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = getClassHierarchy(targetType);
        for (Class<?> sourceCandidate : sourceCandidates) {
            for (Class<?> targetCandidate : targetCandidates) {
                ConvertiblePair convertiblePair = new ConvertiblePair(sourceCandidate, targetCandidate);
                GenericConverter converter = converterMap.get(convertiblePair);
                if (converter != null) {
                    return converter;
                }
            }
        }
        return null;
    }

    /**
     * 获取必须的类型信息
     */
    private ConvertiblePair getRequiredTypeInfo(Object obj) {
        Type[] types = obj.getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        Type[] actualTypeArguments = parameterized.getActualTypeArguments();
        Class<?> sourceType = (Class<?>) actualTypeArguments[0];
        Class<?> targetType = (Class<?>) actualTypeArguments[1];
        return new ConvertiblePair(sourceType, targetType);
    }

    /**
     * 获取类体系，即该类以及该类的所有父类
     */
    private List<Class<?>> getClassHierarchy(Class<?> clazz) {
        List<Class<?>> hierarchy = new ArrayList<>();
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }

    /**
     * 转换器适配器
     */
    private static final class ConverterAdapter implements GenericConverter {
        private final ConvertiblePair typeInfo;

        private final Converter<Object, Object> converter;

        public ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
            this.typeInfo = typeInfo;
            this.converter = (Converter<Object, Object>) converter;
        }

        /**
         * 获取可转换的类型
         */
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        /**
         * 转换对象
         */
        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            return converter.convert(source);
        }
    }

    /**
     * 转换器工厂适配器
     */
    private static final class ConverterFactoryAdapter implements GenericConverter {
        private final ConvertiblePair typeInfo;

        private final ConverterFactory<Object, Object> converterFactory;

        public ConverterFactoryAdapter(ConvertiblePair typeInfo, ConverterFactory<?, ?> converterFactory) {
            this.typeInfo = typeInfo;
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
        }

        /**
         * 获取可转换的类型
         */
        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        /**
         * 转换对象
         */
        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            return converterFactory.getConverter(targetType).convert(source);
        }
    }
}
