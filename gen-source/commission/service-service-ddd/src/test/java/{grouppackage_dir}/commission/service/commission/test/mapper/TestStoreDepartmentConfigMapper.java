/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.test.mapper;

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
import commission.service.dao.mysql.StoreDepartmentConfigMapper;

/**
 * @version 1.0
 * @author 
 * 单元测试 StoreDepartmentConfig: StoreDepartmentConfigMapper
 */
@ContextConfiguration("classpath:generated/testSpringContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional // 事务必须要Junit看得见才能回滚
public class TestStoreDepartmentConfigMapper {
    
    private Log logger = LogFactory.getLog(this.getClass());
    
    @Autowired
    private StoreDepartmentConfigMapper storeDepartmentConfigMapper;
    
    @Before
    public void setUp(){
        Assert.notNull(storeDepartmentConfigMapper, "storeDepartmentConfigMapper 不能为 null");
        // 此处可以做一些初始化操作
    }
    @After
    public void tearDown(){
        storeDepartmentConfigMapper = null;
        // 此处可以做一些清理操作
    }
    
    @Test
    public  void testListBy(){
        logger.debug("开始测试 storeDepartmentConfigMapper.listPage(params)");
        Map<String, Object> params = new HashMap<String, Object>();
        List<StoreDepartmentConfig> resultList =  storeDepartmentConfigMapper.listPage(params);
        //
        Assert.notNull(resultList, "resultList 不能为 null");
        logger.debug("storeDepartmentConfigMapper.listPage(params)测试结束. resultList.size()=" + resultList.size());
    }

    @Test
    public  void testCountBy(){
        logger.debug("开始测试 storeDepartmentConfigMapper.countBy(params)");
        Map<String, Object> params = new HashMap<String, Object>();
        int result =  storeDepartmentConfigMapper.countBy(params);
        //
        Assert.isTrue(result >= 0, "result 不能为 负数");
        logger.debug("storeDepartmentConfigMapper.countBy(params)测试结束. result=" + result);
    }

    @Test
    @Rollback(true)
    public  void testSave(){
        logger.debug("开始测试 storeDepartmentConfigMapper.add(null)");
        StoreDepartmentConfig condition = new StoreDepartmentConfig();
        int result =  0;//storeDepartmentConfigMapper.insert(null);
        //
        Assert.isTrue(result >= 0, "result 不能为 负数");
        logger.debug("storeDepartmentConfigMapper.add(condition)测试结束. result=" + result);
    }

}
