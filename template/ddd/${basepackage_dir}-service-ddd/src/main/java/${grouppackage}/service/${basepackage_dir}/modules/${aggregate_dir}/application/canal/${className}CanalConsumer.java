<#include "/java_copyright.include">
    <#assign className = table.className>
    <#assign classNameLower = className?uncap_first>
    <#assign shortName = table.shortName>

package com.jubaozan.${basepackage}.modules.${aggregate}.application.cache;


import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.jubaozan.c3.framework.utils.canal.CanalMessageProxy;
import com.jubaozan.c3.framework.utils.canal.CanalMessageSink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author jjw
 * @Date ${.now?string["yyy-MM-dd"]}
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ${className}CanalConsumer {

    private final CanalMessageProxy<${className}DO> userMessageProxy = new CanalMessageProxy<>();

    @PostConstruct
    public void initialize() {
        CanalMessageSink.Create<${className}DO> createSink = (tableName, user) -> {

            return true;
        };

        CanalMessageSink.Update<${className}DO> updateSink = (tableName, user, updated) -> {

            return true;
        };

        CanalMessageSink.Delete<${className}DO> deleteSink = (tableName, user) -> {

            return true;
        };
        userMessageProxy.addSink(createSink);
        userMessageProxy.addSink(updateSink);
        userMessageProxy.addSink(deleteSink);
    }

    @RabbitListener(bindings = @QueueBinding(
            key = "key",
            value = @Queue(value = "queueName"),
            exchange = @Exchange(value = "exchangeName", type = ExchangeTypes.TOPIC)))
    public void handleUserMessage(Message   messageInfo) {
        String source = new String(messageInfo.getBody());
        if (logger.isDebugEnabled()) {
            logger.debug("xx更新消息: {}", source);
        }
        userMessageProxy.process(source, ${className}DO.class);
    }


}
