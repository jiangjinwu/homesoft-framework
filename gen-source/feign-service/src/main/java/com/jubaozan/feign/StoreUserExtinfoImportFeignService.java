/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.feign.storeuser;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import commission.storeuser.service.StoreUserExtinfoImportService;

/**
 * @version 1.0
 * @author jjw
 * @date {DATA}
 */
@RestController
@FeignClient("commission-service")
@RequestMapping("/storeuser/storeUserExtinfoImport")
public interface StoreUserExtinfoImportFeignService {

    @Autowired
    private StoreUserExtinfoImportService storeUserExtinfoImportService;

    @ApiOperation("搜索")
    @PostMapping({"/search"})
    ResultHolder<Page<StoreUserExtinfoImportVO>> search(@RequestBody StoreUserExtinfoImportSearchDTO search);

    @PostMapping(value = "")
    @ResponseBody
    ResultHolder create(@Valid @RequestBody StoreUserExtinfoImportCreationDTO storeUserExtinfoImportCreationDTO);

    @GetMapping(value = "/{id}")
    ResultHolder getStoreUserExtinfoImport(@PathVariable(Columns.ID) Long id);

    @PutMapping(value = "/{id}")
    ResultHolder update(@PathVariable(Columns.ID) Long id);


    @DeleteMapping(value = "/{id}")
    ResultHolder delete(@PathVariable(Columns.ID) Long id);
}
