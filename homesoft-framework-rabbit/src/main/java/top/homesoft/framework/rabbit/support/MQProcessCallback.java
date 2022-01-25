package top.homesoft.framework.rabbit.support;

import org.springframework.lang.Nullable;
import top.homesoft.framework.mq.bean.BaseMessage;

@FunctionalInterface
public interface MQProcessCallback<T> {
    @Nullable
    T doProcess(BaseMessage baseMessage);
}
