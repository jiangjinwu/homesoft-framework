/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import com.baomidou.mybatisplus.core.metadata.IPage;

import com.jubaozan.c3.framework.tools.ResultBuilder;
import com.jubaozan.c3.framework.utils.ResultHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.jubaozan.c3.framework.common.tools.ResultBuilder;
import com.jubaozan.c3.framework.constants.Constants;



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
@RequestMapping("/commission/storeDepartmentConfig/admin")
@Slf4j
public class StoreDepartmentConfigAdminController {

    @Autowired
    private StoreDepartmentConfigService storeDepartmentConfigService;
   @Autowired
    private   StoreDepartmentConfigFacade storeDepartmentConfigFacade;

  @ApiOperation("搜索")
  @PostMapping({"/search"})
    ResultHolder<Page<StoreDepartmentConfigVO>> search(@RequestBody StoreDepartmentConfigSearchRequest search){
    return ResultBuilder.success(StoreDepartmentConfigUtils.do2Page( storeDepartmentConfigService.search(search)))
    .withDescription("搜索")
    .build();
  }

  @ApiOperation("更新")
  @PutMapping(value = "")
  public ResultHolder create(@RequestHeader(Constants.KEY_X_C3_AGENTID) Long userId,@Valid @RequestBody StoreDepartmentConfigUpdateRequest request){
      log.info("创建:{},参数:{}", StoreDepartmentConfig,request)
    return ResultBuilder.success(storeDepartmentConfigFacade.getStoreById(store.getId()))
    .withDescription("创建")
    .build();
  }



  @DeleteMapping(value = "/{id}")
  public ResultHolder delete(@PathVariable(Columns.ID) Long id){
    return ResultBuilder.success(storeCacheService.getStoreById(store.getId()))
    .withDescription("搜索")
    .build();
  }

}
