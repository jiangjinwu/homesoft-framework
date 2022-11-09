package com.jubaozan.service.commission.domain.storeuser.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface StoreUserMapper extends BaseMapper<StoreUserDO> {
}
