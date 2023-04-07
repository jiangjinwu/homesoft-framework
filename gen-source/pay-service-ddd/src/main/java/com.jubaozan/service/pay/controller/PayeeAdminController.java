/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package pay.controller;

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



import com.jubaozan.pay.mvc.controller.base.ControllerBase;
import com.jubaozan.pay.util.common.StringNumberUtil;
import com.jubaozan.pay.model.Payee;
import com.jubaozan.pay.service.PayeeService;

/**
 * @version 1.0
 * @author jjw
 */
@Api(tags = "payee管理")
@RestController
@Validated
@RequestMapping("/finance/payee/admin")
@Slf4j
public class PayeeAdminController {

    @Autowired
    private PayeeService payeeService;
   @Autowired
    private   PayeeFacade payeeFacade;

  @ApiOperation("搜索")
  @PostMapping({"/search"})
    ResultHolder<Page<PayeeVO>> search(@RequestBody PayeeSearchRequest search){
    return ResultBuilder.success(PayeeUtils.do2Page( payeeService.search(search)))
    .withDescription("搜索")
    .build();
  }

  @ApiOperation("更新")
  @PutMapping(value = "")
  public ResultHolder create(@RequestHeader(Constants.KEY_X_C3_AGENTID) Long userId,@Valid @RequestBody PayeeUpdateRequest request){
      log.info("创建:{},参数:{}", Payee,request)
    return ResultBuilder.success(payeeFacade.getStoreById(store.getId()))
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
