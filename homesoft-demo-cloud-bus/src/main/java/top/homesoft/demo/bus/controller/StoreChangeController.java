package top.homesoft.demo.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.homesoft.demo.bus.envnt.StoreChengeEvent;

@Controller
public class StoreChangeController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/event/publisher")
    public String publish(){
        StoreChengeEvent e = new StoreChengeEvent();
        applicationEventPublisher.publishEvent(e);
        return "success";
    }
}
