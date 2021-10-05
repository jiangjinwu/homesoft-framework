package top.homesoft.cloudbus.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.homesoft.cloudbus.CacheEvictEvent;

@Api(tags = "corp")
@Slf4j
@RestController
public class CloudBusController {

    @Autowired
    ApplicationEventPublisher  eventPublisher;
    @PostMapping("/bus/publish/myevent")
    public boolean publishMyEvent() {
        CacheEvictEvent event = new CacheEvictEvent("","","");

//        try {
            // 可以注入ApplicationEventPublisher来发送event
            eventPublisher.publishEvent(event);
            // 也可以直接使用
            // applicationContext.publishEvent(event)
            return true;
//        } catch (Exception e) {
//            logger.error("failed in publishing event", e);
//        }
//        return false;
    }
}
