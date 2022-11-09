package com.jubaozan.service.commission.infrastructure.storeuser.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubaozan.service.commission.domain.storeuser.dao.GradeRelationDO;
import com.jubaozan.service.commission.domain.storeuser.dao.mapper.GradeRelationMapper;
import com.jubaozan.service.commission.domain.storeuser.dao.GradeRelationService;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUserID;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiangjinwu
 * @since 2020-11-24
 */
@Service
public class GradeRelationServiceImpl extends ServiceImpl<GradeRelationMapper, GradeRelationDO> implements GradeRelationService {

    @Override
    public List<Long> getFamilyIds(StoreUserID storeUserID) {
        return getChildrenRelations(storeUserID).stream().map(GradeRelationDO::getUserId).collect(Collectors.toList());
    }

    public List<GradeRelationDO> getChildrenRelations(StoreUserID storeUserID) {
        QueryWrapper<GradeRelationDO> queryWrapper = new QueryWrapper();
        LambdaQueryWrapper<GradeRelationDO> lambdaQueryWrapper = queryWrapper.lambda();
        lambdaQueryWrapper.eq(GradeRelationDO::getStoreId,storeUserID.getStoreId());
        lambdaQueryWrapper.and(wq->wq.or().eq(GradeRelationDO::getP1,storeUserID.getUserId())
            .or().eq(GradeRelationDO::getP2,storeUserID.getUserId()).or()
            .eq(GradeRelationDO::getP3,storeUserID.getUserId()).or()
            .eq(GradeRelationDO::getP4,storeUserID.getUserId())
            .or().eq(GradeRelationDO::getP5,storeUserID.getUserId()) );
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public GradeRelationDO getGradeRelation(StoreUserID storeUserID) {
        QueryWrapper<GradeRelationDO> queryWrapper = new QueryWrapper();
        LambdaQueryWrapper<GradeRelationDO> lambdaQueryWrapper = queryWrapper.lambda();
        lambdaQueryWrapper.eq(GradeRelationDO::getStoreId,storeUserID.getStoreId());
        lambdaQueryWrapper.eq(GradeRelationDO::getUserId,storeUserID.getUserId());
        return baseMapper.selectOne(lambdaQueryWrapper);
    }
}
