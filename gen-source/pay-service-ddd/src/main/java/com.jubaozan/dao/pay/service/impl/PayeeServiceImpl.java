/*
 * 创 建 人: jjw
 * 创建时间: 2023-3-24 14:21:17?string("yyyyMMdd")
 */
/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package pay.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubaozan.dao.pay.service.PayeeService;
import com.jubaozan.dao.pay.domain.PayeeDO;


/**
 * @auther jjw
 */
@Service
public class PayeeServiceImpl extends ServiceImpl<PayeeMapper,PayeeDO>implements PayeeService{

    /** isEntityTable = false**/

    @Override
    public IPage<PayeeDO>search(PayeeSearchDTO request){
        return baseMapper.search(new Page<>(request.getPageNo(),request.getPageSize(),request);
    }

    public PayeeDO getByUserId(UserId userId){
        QueryWrapper<PayeeDO> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<PayeeDO> lambdaQueryWrapper =queryWrapper.lambda();
        lambdaQueryWrapper.eq(PayeeDO::getUserId,userId.getId())
        return baseMapper.selectList(lambdaQueryWrapper).stream().findFirst().orElse(null);
    }


}
