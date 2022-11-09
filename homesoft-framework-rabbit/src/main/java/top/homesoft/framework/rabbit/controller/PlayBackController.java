package top.homesoft.framework.rabbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.homesoft.framework.mq.bean.BaseMessage;
import top.homesoft.framework.rabbit.support.PlayBackMessageService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PlayBackController {
    @Autowired
    PlayBackMessageService playBackMessageService;

    @RequestMapping(path = "/playBack", method = GET, consumes = "*/*")
    public Object repay(BaseMessage baseMessage) throws Exception {
        return playBackMessageService.playBack(baseMessage.getId(),baseMessage.getBizCode());
    }
}
