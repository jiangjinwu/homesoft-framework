/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.feign.commission;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import commission.service.commission.service.StoreDepartmentConfigService;

/**
 * @version 1.0
 * @author 
 */
@RestController
@FeignClient("commission.service-service")
@RequestMapping("/commission/storeDepartmentConfig")
public interface StoreDepartmentConfigFeignService {
    
    @Autowired
    private StoreDepartmentConfigService storeDepartmentConfigService;

    @ApiOperation("搜索")
    @PostMapping({"/search"})
    ResultHolder<Page<StoreDepartmentConfigVO>> search(@RequestBody StoreDepartmentConfigSearchDTO search);

    @PostMapping(value = "")
    @ResponseBody
    ResultHolder create(@Valid @RequestBody StoreDepartmentConfigCreationDTO storeDepartmentConfigCreationDTO);

    @GetMapping(value = "/{id}")
    ResultHolder getStoreDepartmentConfig(@PathVariable(Columns.ID) Long id);

    @PutMapping(value = "/{id}")
    ResultHolder update(@PathVariable(Columns.ID) Long id);
    

    @DeleteMapping(value = "/{id}")
    ResultHolder delete(@PathVariable(Columns.ID) Long id);
}
