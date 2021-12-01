package top.homesoft.demo.bus.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StoreChangeListener {
    @EventListener(AckRemoteApplicationEvent.class)
    public void ackConsumerConfirm(AckRemoteApplicationEvent event) {
        // 相当于回调函数，在消息成功被消费时调用，event里能取得actId: event.getAckId 和 消息的ID：event.getId()
        logger.info("[工作流事件消息确认]消费者端: " + event.getOriginService() + ", 服务消费确认 ackId :" + event.getAckId());
    }
}
