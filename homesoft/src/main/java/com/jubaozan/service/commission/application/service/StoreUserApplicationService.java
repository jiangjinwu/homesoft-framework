package com.jubaozan.service.commission.application.service;

import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserDO;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserService;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUserID;
import com.jubaozan.service.commission.domain.storeuser.dto.StoreUserTreeNode;
import com.jubaozan.service.commission.domain.storeuser.service.StoreUserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class StoreUserApplicationService {
    @Autowired  StoreUserDomainService storeUserDomainService;
    @Autowired StoreUserService storeUserService;

    @Transactional
    public void modifyReferee(Long storeId,Long userId,Long newRefereeId){
        StoreUserID newRefereeStoreUserID = new StoreUserID(storeId,newRefereeId);
        StoreUserDO newReferee = storeUserService.loadByStoreUserId(newRefereeStoreUserID);

        StoreUserID storeUserID = new StoreUserID(storeId,userId);
        StoreUserTreeNode rootTreeNode = storeUserDomainService.getRefereeFamilyJunior(storeUserID);

        Long fansLevelId = 1L;
        List<StoreUserTreeNode>  fansTreeNodeList = rootTreeNode.getFamilyJunior(Collections.singletonList(fansLevelId));

    }

}
