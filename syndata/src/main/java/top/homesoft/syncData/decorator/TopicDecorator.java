package top.homesoft.syncData.decorator;

import org.springframework.stereotype.Service;
import top.homesoft.syncData.mq.producer.SyncDataProducer;
import top.homesoft.syncData.vo.SyncDataMessage;
@Service
public class TopicDecorator {
    private final SyncDataProducer syncDataProducer;

    public TopicDecorator(SyncDataProducer syncDataProducer) {
        this.syncDataProducer = syncDataProducer;
    }

    public void sendMessage(SyncDataMessage message, String routeKey) {
        this.syncDataProducer.sendSyncDataMessage(message, routeKey);
    }
}
