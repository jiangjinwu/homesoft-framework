package com.jubaozan.service.commission.domain.storeuser.service;

import com.jubaozan.service.commission.domain.storeuser.assembler.StoreUserAssembler;
import com.jubaozan.service.commission.domain.storeuser.dao.GradeRelationService;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserDO;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserService;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUserID;
import com.jubaozan.service.commission.domain.storeuser.dto.StoreUserTreeNode;
import com.jubaozan.service.commission.level.StoreUserWeightEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StoreUserDomainService {
    @Autowired
    StoreUserService storeUserService;
    @Autowired
    GradeRelationService gradeRelationService;

    //获得推荐关系所有下级
    public StoreUserTreeNode getRefereeFamilyJunior(StoreUserID storeUserID) {
        StoreUserDO root =  storeUserService.loadByStoreUserId(storeUserID);
        StoreUserTreeNode rootNode = new StoreUserTreeNode();
        rootNode.setStoreUser(root);
        doGetRefereeFamilyJunior(rootNode);
        return rootNode;
    }


    List<StoreUserTreeNode> doGetRefereeFamilyJunior(StoreUserTreeNode storeUserTreeNode) {
        StoreUserID storeUserID = new StoreUserID(storeUserTreeNode.getStoreUser().getStoreId(), storeUserTreeNode.getStoreUser().getUserId());
        List<StoreUserDO> storeUserDOS = storeUserService.loadStoreUserListByReferee(storeUserID);
        List<StoreUserTreeNode> storeUserNestList = StoreUserAssembler.INSTANCE.toStoreUserTreeNode(storeUserDOS);
        storeUserTreeNode.setChildren(storeUserNestList);
        for (StoreUserTreeNode userNest : storeUserNestList) {
            if (!userNest.getStoreUser().getWeight().equals(StoreUserWeightEnum.PLATFORM.getValue())) {
                doGetRefereeFamilyJunior(userNest);
            }
        }
        return storeUserNestList;
    }


   public List<StoreUserDO> getSpecificationLevelStoreUser(StoreUserTreeNode storeUserTreeNode,Integer weight) {
        List<StoreUserTreeNode> storeUserNestList = storeUserTreeNode.getFamilyJunior(null);
        return storeUserNestList.stream().map(StoreUserTreeNode::getStoreUser).filter(storeUserDO -> storeUserDO.getWeight().equals(weight)).collect(Collectors.toList());
    }

    //获得推荐关系所有上级
    public  LinkedList<StoreUserDO> getRefereeFamilySenior(StoreUserID storeUserID) {
        StoreUserDO root =  storeUserService.loadByStoreUserId(storeUserID);
        LinkedList<StoreUserDO> list = new LinkedList<>();
        list.addFirst(root);
        return  recursionGetReferee(list);
    }

    LinkedList<StoreUserDO> recursionGetReferee( LinkedList<StoreUserDO> list){
        StoreUserDO last = list.getLast();
        StoreUserDO referee = storeUserService.loadByStoreUserId(new StoreUserID(last.getStoreId(),last.getRefereeId()));
        if(Objects.nonNull(referee)) {
            list.addLast(referee);
            if(referee.getWeight() < 255){
                recursionGetReferee(list);
            }
        }
        return list;
    }

   public List<StoreUserTreeNode> getLeafChildren(StoreUserTreeNode storeUserTreeNode){
        List<StoreUserTreeNode> result = new ArrayList<>();
        return recursionGetLeafChildren(result,storeUserTreeNode);
    }


    List<StoreUserTreeNode> recursionGetLeafChildren(List<StoreUserTreeNode> result,StoreUserTreeNode storeUserTreeNode){
        storeUserTreeNode.getChildren().forEach(child->{
            if(child.getChildren().size()==0){
                result.add(child);
            }
            recursionGetLeafChildren(result,child);
        });
        return result;
    }
}
