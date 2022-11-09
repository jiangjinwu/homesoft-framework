<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.feign.${namespace};

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import ${basepackage}.${namespace}.service.${className}Service;

/**
 * @version 1.0
 * @author 
 */
@RestController
@FeignClient("${basepackage}-service")
@RequestMapping("/${namespace}/${classNameLower}")
public interface ${className}FeignService {
    
    @Autowired
    private ${className}Service ${classNameLower}Service;

    @ApiOperation("搜索")
    @PostMapping({"/search"})
    ResultHolder<Page<${className}VO>> search(@RequestBody ${className}SearchDTO search);

    @PostMapping(value = "")
    @ResponseBody
    ResultHolder create(@Valid @RequestBody ${className}CreationDTO ${classNameLower}CreationDTO);

    @GetMapping(value = "/{id}")
    ResultHolder get${className}(@PathVariable(Columns.ID) Long id);

    @PutMapping(value = "/{id}")
    ResultHolder update(@PathVariable(Columns.ID) Long id);
    

    @DeleteMapping(value = "/{id}")
    ResultHolder delete(@PathVariable(Columns.ID) Long id);
}
