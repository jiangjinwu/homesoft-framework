package top.homesoft.framework.rabbit.support;

import top.homesoft.framework.mq.bean.BaseMessage;

/**
 * @author nolan jiang
 * 未成功处理的消息通过 repository 存储起来，默认存到redis中。用户可自行实现此接口。
 */
public interface PlayBackMessageRepository {

    BaseMessage put(BaseMessage message);

    BaseMessage get(String messageId);

    BaseMessage delete(String messageId);
}
