<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.controller;

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
@RequestMapping("/${namespace}/${classNameLower}/admin")
@Slf4j
public class ${className}AdminController {

    @Autowired
    private ${className}Service ${classNameLower}Service;
   @Autowired
    private   ${className}Facade ${classNameLower}Facade;

  @ApiOperation("搜索")
  @PostMapping({"/search"})
    ResultHolder<Page<${className}VO>> search(@RequestBody ${className}SearchRequest search){
    return ResultBuilder.success(${className}Utils.do2Page( ${classNameLower}Service.search(search)))
    .withDescription("搜索")
    .build();
  }

  @ApiOperation("更新")
  @PutMapping(value = "")
  public ResultHolder create(@RequestHeader(Constants.KEY_X_C3_AGENTID) Long userId,@Valid @RequestBody ${className}UpdateRequest request){
      log.info("创建:{},参数:{}", ${className},request)
    return ResultBuilder.success(${classNameLower}Facade.getStoreById(store.getId()))
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
