/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package pay.feign.finance;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import pay.finance.service.PayeeService;

/**
 * @version 1.0
 * @author jjw
 * @date {DATA}
 */
@RestController
@FeignClient("pay-service")
@RequestMapping("/finance/payee")
public interface PayeeFeignService {

    @Autowired
    private PayeeService payeeService;

    @ApiOperation("搜索")
    @PostMapping({"/search"})
    ResultHolder<Page<PayeeVO>> search(@RequestBody PayeeSearchDTO search);

    @PostMapping(value = "")
    @ResponseBody
    ResultHolder create(@Valid @RequestBody PayeeCreationDTO payeeCreationDTO);

    @GetMapping(value = "/{id}")
    ResultHolder getPayee(@PathVariable(Columns.ID) Long id);

    @PutMapping(value = "/{id}")
    ResultHolder update(@PathVariable(Columns.ID) Long id);


    @DeleteMapping(value = "/{id}")
    ResultHolder delete(@PathVariable(Columns.ID) Long id);
}
