/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.test.service;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import commission.service.model.StoreDepartmentConfig;
import commission.service.service.StoreDepartmentConfigService;

/**
 * @version 1.0
 * @author 
 * 单元测试 StoreDepartmentConfig: StoreDepartmentConfigService
 */
@ContextConfiguration("classpath:generated/testSpringContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional // 事务必须要Junit看得见才能回滚
public class TestStoreDepartmentConfigService {
    
    private Log logger = LogFactory.getLog(this.getClass());
    
    @Autowired
    private StoreDepartmentConfigService storeDepartmentConfigService;
    
    @Before
    public void setUp(){
        Assert.notNull(storeDepartmentConfigService, "storeDepartmentConfigService 不能为 null");
        // 此处可以做一些初始化操作
    }
    @After
    public void tearDown(){
        storeDepartmentConfigService = null;
        // 此处可以做一些清理操作
    }
    
    @Test
    public  void testListBy(){
        logger.debug("开始测试 storeDepartmentConfigService.listPage(params)");
        Map<String, Object> params = new HashMap<String, Object>();
        List<StoreDepartmentConfig> resultList =  storeDepartmentConfigService.listPage(params);
        //
        Assert.notNull(resultList, "resultList 不能为 null");
        logger.debug("storeDepartmentConfigService.listPage(params)测试结束. resultList.size()=" + resultList.size());
    }

    @Test
    public  void testCountBy(){
        logger.debug("开始测试 storeDepartmentConfigService.countBy(params)");
        Map<String, Object> params = new HashMap<String, Object>();
        int result =  storeDepartmentConfigService.countBy(params);
        //
        Assert.isTrue(result >= 0, "result 不能为 负数");
        logger.debug("storeDepartmentConfigService.countBy(params)测试结束. result=" + result);
    }

    @Test
    @Rollback(true)
    public  void testSave(){
        logger.debug("开始测试 storeDepartmentConfigService.add(null)");
        StoreDepartmentConfig condition = new StoreDepartmentConfig();
        int result =  storeDepartmentConfigService.add(null);
        //
        Assert.isTrue(result >= 0, "result 不能为 负数");
        logger.debug("storeDepartmentConfigService.add(condition)测试结束. result=" + result);
    }

}
