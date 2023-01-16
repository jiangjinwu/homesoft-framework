<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${grouppackage}.service.${basepackage}.controller;

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
private final ${className}Facade ${classNameLower}Facade;


import ${grouppackage}.${basepackage}.mvc.controller.base.ControllerBase;
import ${grouppackage}.${basepackage}.util.common.StringNumberUtil;
import ${grouppackage}.${basepackage}.model.${className};
import ${grouppackage}.${basepackage}.service.${className}Service;

/**
 * @version 1.0
 * @author jjw
 */
@Api(tags = "${classNameLower}管理")
@RestController
@Validated
@RequestMapping("/${aggregate}/${classNameLower}")
@Slf4j
public class ${className}Controller {

    @Autowired
    private ${className}Facade ${classNameLower}Facade;


  @ApiOperation("创建")
  @PostMapping(value = "")
  public ResultHolder create(@RequestHeader(Constants.KEY_X_C3_AGENTID) Long userId,@Valid @RequestBody ${className}CreationRequest request){
      log.info("创建xx,参数:{}", request)
    return ResultBuilder.success(${classNameLower}Facade.create(store.getId()))
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
