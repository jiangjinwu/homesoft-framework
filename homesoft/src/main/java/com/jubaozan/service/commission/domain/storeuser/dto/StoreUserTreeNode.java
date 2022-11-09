package com.jubaozan.service.commission.domain.storeuser.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.jubaozan.service.commission.domain.storeuser.dao.StoreUserDO;

import com.jubaozan.service.commission.domain.storeuser.domain.StoreUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class StoreUserTreeNode {
    StoreUser maxChildNode;
    StoreUserDO parent;
    StoreUserDO storeUser;
    Boolean isTemp;
    Boolean isLeaf;
    Long newParentId;
    Long newRefereeId;
    Long newSameLevelRefereeId;

    List<StoreUserTreeNode> children;

    public List<Long> getIds(List ids){
        ids.add(storeUser.getUserId());
        for (StoreUserTreeNode child : children) {
            child.getIds(ids);
        }
        return ids;
    }

    public List<StoreUserDO> getRefererChildren(List<StoreUserDO> result){
        result.add(storeUser);
        for (StoreUserTreeNode child : children) {
            child.getStoreUser();
        }
        return result;
    }

    public List<StoreUserTreeNode> getFamilyJunior(List<Long> levelIds){
        List<StoreUserTreeNode> result = new ArrayList<>();
        doGetChildrenStoreUser(result,children);
        return result;
    }

    public List<StoreUserTreeNode> getChildren(){
        List<StoreUserTreeNode> result = new ArrayList<>();
        for (StoreUserTreeNode child : children) {
            if (storeUser.getWeight() < child.getStoreUser().getWeight()) {
                result.add(child);
            }
        }
        return result;
    }

    public List<StoreUserTreeNode> doGetChildrenStoreUser(List<StoreUserTreeNode> result,List<StoreUserTreeNode> storeUserTreeNodes ){
        for (StoreUserTreeNode storeUserTreeNode : storeUserTreeNodes) {
            List<StoreUserTreeNode> children = storeUserTreeNode.getChildren();
            if(CollectionUtil.isNotEmpty(children)) {
                result.addAll(children);
            }
            storeUserTreeNode.doGetChildrenStoreUser(result,children);
        }
        return result;
    }

    public static StoreUserTreeNode from(StoreUserDO obj){
        StoreUserTreeNode vo = new StoreUserTreeNode();
        vo.storeUser = obj;
        return vo;
    }

    public static List<StoreUserTreeNode> fromList(List<StoreUserDO> objList){
        return  objList.stream().map(StoreUserTreeNode::from).collect(Collectors.toList());
    }

    public void setChildren(List<StoreUserTreeNode> children) {
        this.children = children;
        children.forEach(child->{
            child.setParent(this.getStoreUser());
        });
    }
}
