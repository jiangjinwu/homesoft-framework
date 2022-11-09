package com.jubaozan.service.commission.interfaces.storeuser;

import com.jubaozan.service.commission.application.service.StoreUserApplicationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"平台用户接口"})
@RestController
@Validated
@Slf4j
@RequestMapping("/storeUser")
public class StoreUserController {
    @Autowired
    StoreUserApplicationService storeUserApplicationService;
    @GetMapping("/1")
    public ResponseEntity<Object> getAllBooks(Long storeId,Long userId,Long newRefereeId) {
        storeUserApplicationService.modifyReferee(storeId,userId,newRefereeId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
