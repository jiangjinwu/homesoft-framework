package top.homesoft.framework.rabbit.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.homesoft.framework.mq.bean.BaseMessage;
import top.homesoft.framework.mq.bean.BaseMessageType;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractMQProcessTemplate<T extends  BaseMessage> {

    protected final Log logger = LogFactory.getLog(this.getClass());

    public  void  execute(BaseMessage baseMessage,Consumer<BaseMessage> filter){
        try {

            if(Objects.isNull(baseMessage.getMessageType())){
                baseMessage.setMessageType(BaseMessageType.ORIGINAL);
            }
            filter.accept(baseMessage);
            if(baseMessage.getMessageType().equals(BaseMessageType.PLAYBACK)){
             //标记为已处理
                finishPlayBackMessage(baseMessage.getId());
            }

        }catch (Exception e){
            handleNotProcessMesssage(baseMessage);
        }
    }


    protected abstract  void persistMessage(BaseMessage message);

    protected abstract  void finishPlayBackMessage(String messageId);

    void handleNotProcessMesssage(BaseMessage baseMessage){

        try {
            baseMessage.setMessageType(BaseMessageType.PLAYBACK);
            persistMessage(baseMessage);
        }catch (Exception e){
            logger.error("记录未处理消息异常",e);
        }
    }
}
