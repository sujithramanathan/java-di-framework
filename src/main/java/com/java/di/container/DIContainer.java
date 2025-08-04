package com.java.di.container;

import com.java.di.annotation.Component;
import com.java.di.annotation.Inject;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DIContainer {

    private final Map<Class<?>, Object> instances = new HashMap<Class<?>, Object>();

    public void register(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> clazz : annotatedClasses) {
            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                instances.put(clazz, instance);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void injectDependencies(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object dependency = findMatchingInstance(field.getType());
                if (null != dependency) {
                    try {
                        field.setAccessible(true);
                        field.set(obj, dependency);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new RuntimeException(field.getName()+" Not annotated with Component " );
                }
            }
        }
    }

    private Object findMatchingInstance(Class<?> type) {
        for (Map.Entry<Class<?>, Object> entry : instances.entrySet()) {
            if (type.isAssignableFrom(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(findMatchingInstance(clazz));
    }

}
