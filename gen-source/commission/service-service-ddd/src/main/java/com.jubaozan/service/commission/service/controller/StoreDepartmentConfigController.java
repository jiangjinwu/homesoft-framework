/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package com.jubaozan.service.commission.service.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.jubaozan.c3.framework.common.tools.ResultBuilder;
import com.jubaozan.c3.framework.constants.Constants;
private final StoreDepartmentConfigFacade storeDepartmentConfigFacade;


import com.jubaozan.commission.service.mvc.controller.base.ControllerBase;
import com.jubaozan.commission.service.util.common.StringNumberUtil;
import com.jubaozan.commission.service.model.StoreDepartmentConfig;
import com.jubaozan.commission.service.service.StoreDepartmentConfigService;

/**
 * @version 1.0
 * @author jjw
 */
@Api(tags = "storeDepartmentConfig管理")
@RestController
@Validated
@RequestMapping("/commission/storeDepartmentConfig")
@Slf4j
public class StoreDepartmentConfigController {

    @Autowired
    private StoreDepartmentConfigFacade storeDepartmentConfigFacade;


  @ApiOperation("创建")
  @PostMapping(value = "")
  public ResultHolder create(@RequestHeader(Constants.KEY_X_C3_AGENTID) Long userId,@Valid @RequestBody StoreDepartmentConfigCreationRequest request){
      log.info("创建xx,参数:{}", request)
    return ResultBuilder.success(storeDepartmentConfigFacade.create(store.getId()))
    .withDescription("创建")
    .build();
  }



  @GetMapping(value = "/{id}")
  public ResultHolder delete(@PathVariable(Columns.ID) Long id){
    return ResultBuilder.success(storeCacheService.getStoreById(store.getId()))
    .withDescription("搜索")
    .build();
  }

}
