package com.jubaozan.service.commission.domain.storeuser.dao;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUserID;

import java.util.List;

/**
 * @Author: jiangjinwu
 * @Date: 2020/12/4
 */
public interface GradeRelationService extends IService<GradeRelationDO> {

   List<Long> getFamilyIds(StoreUserID storeUserID);


    GradeRelationDO getGradeRelation(StoreUserID storeUserID);
}
