<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.controller;

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
@Api(tags = "自提点")
@RestController
@Validated
@RequestMapping("/${aggregate}/${classNameLower}")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${classNameLower}Service;



  @ApiOperation("搜索")
  @PostMapping({"/search"})
    ResultHolder<Page<${classNameLower}VO>> search(@RequestBody ${className}SearchDTO search){
    return ResultBuilder.success(${classNameLower}Service.getStoreById(store.getId()))
    .withDescription("搜索")
    .build();
  }
  @ApiOperation("搜索")
  @PostMapping(value = "")
  public ResultHolder create(@RequestHeader(Constants.KEY_X_C3_AGENTID) Long userId,@Valid @RequestBody StoreRelationCreationDTO storeRelationCreationDTO){
    return ResultBuilder.success(${classNameLower}Facade.getStoreById(store.getId()))
    .withDescription("搜索")
    .build();
  }

  @GetMapping(value = "/{id}")
  public ResultHolder getStoreRelation(@PathVariable(Columns.ID) Long id){
    return ResultBuilder.success(${classNameLower}Facade.getStoreById(store.getId()))
    .withDescription("搜索")
    .build();
  }

  @PutMapping(value = "/{id}")
  public ResultHolder update(@PathVariable(Columns.ID) Long id){
    return ResultBuilder.success(${classNameLower}Facade.getStoreById(store.getId()))
    .withDescription("搜索")
    .build();
  }


  @DeleteMapping(value = "/{id}")
  public ResultHolder delete(@PathVariable(Columns.ID) Long id){
    return ResultBuilder.success(storeCacheService.getStoreById(store.getId()))
    .withDescription("搜索")
    .build();
  }

}
