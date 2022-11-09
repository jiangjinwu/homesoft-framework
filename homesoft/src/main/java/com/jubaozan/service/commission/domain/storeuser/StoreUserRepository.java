package com.jubaozan.service.commission.domain.storeuser;

import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import com.github.meixuesong.aggregatepersistence.DataObjectUtils;
import com.jubaozan.service.commission.exception.EntityNotFoundException;
import com.jubaozan.service.commission.domain.storeuser.assembler.StoreUserAssembler;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserDO;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserService;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUser;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUserID;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class StoreUserRepository {

    StoreUserService storeUserService;

    public Aggregate<StoreUser> findById(StoreUserID storeUserID) {
        StoreUserDO orderDO = storeUserService.loadByStoreUserId(storeUserID);
        if (orderDO == null) {
            throw new EntityNotFoundException("StoreUser(" + storeUserID + ") not found");
        }
        StoreUser order = orderDO.toStoreUser();
        return AggregateFactory.createAggregate(order);
    }


    public void save(Aggregate<StoreUser> userAggregate){
        if(userAggregate.isNew()){
            insertNewAggregate(userAggregate);
        }else if(userAggregate.isChanged()){
            updateAggregateRoot(userAggregate);
        }
    }


    private void updateAggregateRoot(Aggregate<StoreUser> orderAggregate) {
        StoreUserDO newOrderDO =   StoreUserAssembler.INSTANCE.domain2DO(orderAggregate.getRoot());
        Set<String> changedFields = DataObjectUtils.getChangedFields(orderAggregate.getRootSnapshot(), orderAggregate.getRoot());
        storeUserService.updateById(newOrderDO);
    }

    private void insertNewAggregate(Aggregate<StoreUser> orderAggregate) {
        StoreUser order = orderAggregate.getRoot();
        storeUserService.save(StoreUserAssembler.INSTANCE.domain2DO(order));
    }
}
