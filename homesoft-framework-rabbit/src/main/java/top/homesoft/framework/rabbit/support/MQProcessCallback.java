package top.homesoft.framework.rabbit.support;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface MQProcessCallback<T> {
    @Nullable
    T doProcess();
}
