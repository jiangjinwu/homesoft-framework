package com.jubaozan.service.commission.infrastructure.storeuser.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserDO;
import com.jubaozan.service.commission.domain.storeuser.dao.mapper.StoreUserMapper;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserService;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUserID;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiangjinwu
 * @since 2020-11-24
 */
@Service
public class StoreUserServiceImpl extends ServiceImpl<StoreUserMapper, StoreUserDO> implements StoreUserService {

    public StoreUserDO loadByMobile(Long storeId, String mobile){
        LambdaQueryWrapper<StoreUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StoreUserDO::getStoreId,storeId);
        queryWrapper.eq(StoreUserDO::getMobile,mobile);
        return this.getOne(queryWrapper);
    }

    @Override
    public StoreUserDO loadByStoreUserId(StoreUserID storeUserID) {
        LambdaQueryWrapper<StoreUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StoreUserDO::getStoreId,storeUserID.getStoreId());
        queryWrapper.eq(StoreUserDO::getUserId,storeUserID.getUserId());
        return this.getOne(queryWrapper);
    }

    @Override
    public List<StoreUserDO> loadStoreUserListByParent(Long storeId, List<Long> parentIds, Long levelId) {
        if(CollectionUtil.isEmpty(parentIds)){
            return Collections.emptyList();
        }
        LambdaQueryWrapper<StoreUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StoreUserDO::getStoreId,storeId);
        queryWrapper.in(StoreUserDO::getParentId,parentIds);
        return this.list(queryWrapper);
    }

    @Override
    public List<StoreUserDO> loadStoreUserListByReferee(StoreUserID storeUserID) {
        LambdaQueryWrapper<StoreUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StoreUserDO::getStoreId,storeUserID.getStoreId());
        queryWrapper.in(StoreUserDO::getRefereeId,storeUserID.getUserId());
        return this.list(queryWrapper);
    }


}
