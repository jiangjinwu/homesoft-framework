package com.jubaozan.service.commission.domain.storeuser.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUserID;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiangjinwu
 * @since 2020-11-24
 */
public interface StoreUserService extends IService<StoreUserDO> {
    StoreUserDO loadByMobile(Long storeId, String mobile);

    StoreUserDO loadByStoreUserId(StoreUserID storeUserID);


    List<StoreUserDO> loadStoreUserListByParent(Long storeId, List<Long> parentIds, Long levelId);

    List<StoreUserDO> loadStoreUserListByReferee(StoreUserID storeUserID);
}
