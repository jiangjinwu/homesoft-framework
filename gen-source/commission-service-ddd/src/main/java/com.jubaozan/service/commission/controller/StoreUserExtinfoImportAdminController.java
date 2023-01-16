/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.controller;

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



import com.jubaozan.commission.mvc.controller.base.ControllerBase;
import com.jubaozan.commission.util.common.StringNumberUtil;
import com.jubaozan.commission.model.StoreUserExtinfoImport;
import com.jubaozan.commission.service.StoreUserExtinfoImportService;

/**
 * @version 1.0
 * @author jjw
 */
@Api(tags = "storeUserExtinfoImport管理")
@RestController
@Validated
@RequestMapping("/storeuser/storeUserExtinfoImport/admin")
@Slf4j
public class StoreUserExtinfoImportAdminController {

    @Autowired
    private StoreUserExtinfoImportService storeUserExtinfoImportService;
   @Autowired
    private   StoreUserExtinfoImportFacade storeUserExtinfoImportFacade;

  @ApiOperation("搜索")
  @PostMapping({"/search"})
    ResultHolder<Page<StoreUserExtinfoImportVO>> search(@RequestBody StoreUserExtinfoImportSearchRequest search){
    return ResultBuilder.success(StoreUserExtinfoImportUtils.do2Page( storeUserExtinfoImportService.search(search)))
    .withDescription("搜索")
    .build();
  }

  @ApiOperation("更新")
  @PutMapping(value = "")
  public ResultHolder create(@RequestHeader(Constants.KEY_X_C3_AGENTID) Long userId,@Valid @RequestBody StoreUserExtinfoImportUpdateRequest request){
      log.info("创建:{},参数:{}", StoreUserExtinfoImport,request)
    return ResultBuilder.success(storeUserExtinfoImportFacade.getStoreById(store.getId()))
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
