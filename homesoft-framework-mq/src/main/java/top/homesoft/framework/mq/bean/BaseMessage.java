package top.homesoft.framework.mq.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseMessage  implements Serializable {
    String id;
    String bizCode;
    BaseMessageType messageType = BaseMessageType.ORIGINAL;
}
