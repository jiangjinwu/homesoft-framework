package top.homesoft.framework.rabbit.support;

import com.rabbitmq.client.ConfirmCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import top.homesoft.framework.mq.bean.BaseMessage;
import top.homesoft.framework.rabbit.exception.PlayBackMessageNotExistsException;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class PlayBackMessageService<T extends BaseMessage>   {
    final PlayBackMessageRepository playBackMessageRepository;
    final RabbitTemplate rabbitTemplate;
    public PlayBackMessageService(PlayBackMessageRepository playBackMessageRepository, RabbitTemplate rabbitTemplate) {
        this.playBackMessageRepository = playBackMessageRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    public String playBack(String id, String bizCode) throws PlayBackMessageNotExistsException {
        Optional<BaseMessage> baseMessageOptional = playBackMessageRepository.get(bizCode,id+"");
        if(baseMessageOptional.isPresent()) {
            BaseMessage baseMessage = baseMessageOptional.get();
            baseMessage.setPlaybackTimes(baseMessage.getPlaybackTimes() + 1);
            // 同步发送消息
            String queue = baseMessage.getMessageProperties().getConsumerQueue();
            rabbitTemplate.convertAndSend(queue, baseMessage);
            return  String.format("id:%s bizCode:%s 消息回放发送成功",id,bizCode);
        }else{
            throw new PlayBackMessageNotExistsException();
        }
    }

   private Object invoke(String queue,BaseMessage baseMessage){
       final Boolean[] result = {null};



        this.rabbitTemplate.invoke(new RabbitOperations.OperationsCallback<Object>() {
            @Override
            public Object doInRabbit(RabbitOperations operations) {
                // 同步发送消息
                operations.convertAndSend(queue, baseMessage);
                logger.info("[doInRabbit][发送消息完成]");
                // 等待确认
                operations.waitForConfirms(0); // timeout 参数，如果传递 0 ，表示无限等待
                logger.info("[doInRabbit][等待 Confirm 完成]");
                return null;
            }

        }, new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                result[0] = true;
                logger.info("[handle][Confirm 成功]");
            }

        }, new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                result[0] = false;
                logger.info("[handle][Confirm 失败]");
            }

        });

       if(Objects.nonNull(result[0]) && result[0]==false ){
           return  String.format("%s 消息发送成功，处理失败",baseMessage);
       }
       return  String.format("id:%s bizCode:%s 消息回放成功",baseMessage.getId(),baseMessage.getBizCode());
    }


}
