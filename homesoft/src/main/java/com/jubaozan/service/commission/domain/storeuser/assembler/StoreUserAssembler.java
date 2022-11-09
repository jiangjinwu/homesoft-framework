package com.jubaozan.service.commission.domain.storeuser.assembler;


import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserDO;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUser;
import com.jubaozan.service.commission.domain.storeuser.dto.StoreUserTreeNode;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * @ClassName StoreUserMapping
 * @Description
 * @Author WuShuo
 * @Date 2020/11/17
 * @Version 1.0
 **/

@Mapper
public interface StoreUserAssembler {


    StoreUserAssembler INSTANCE = Mappers.getMapper(StoreUserAssembler.class);


    StoreUser do2Domain(StoreUserDO storeUserDO);

    StoreUserDO domain2DO(StoreUser storeUserDO);


    @IterableMapping(elementTargetType = StoreUserTreeNode.class)
    List<StoreUserTreeNode> toStoreUserTreeNode(List<StoreUserDO> list);

}
