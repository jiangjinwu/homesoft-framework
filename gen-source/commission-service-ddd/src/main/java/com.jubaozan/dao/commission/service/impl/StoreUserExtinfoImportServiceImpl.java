/*
 * 文 件 名:  StoreUserExtinfoImportServiceImpl.java
 * 创 建 人:
 * 创建时间:
 */

package commission.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubaozan.dao.commission.service.StoreUserExtinfoImportService;
import com.jubaozan.dao.commission.domain.StoreUserExtinfoImportDO;


/**
 * @auther jjw
 */
@Service
public class StoreUserExtinfoImportServiceImpl extends ServiceImpl<StoreUserExtinfoImportMapper,StoreUserExtinfoImportDO>implements StoreUserExtinfoImportService{

    @Override
    public IPage<StoreUserExtinfoImportDO>search(StoreUserExtinfoImportSearchRequest request){
        QueryWrapper<StoreUserExtinfoImportDO> queryWrapper=new QueryWrapper();
        LambdaQueryWrapper<StoreUserExtinfoImportDO>lambdaQueryWrapper=queryWrapper.lambda();
        if(StringUtils.isNotEmpty(request.getSearchKey())){
            Consumer<LambdaQueryWrapper<StoreUserExtinfoImportDO>>searchKey=wrapper->wrapper.like(StoreUserExtinfoImportDO::getName,request.getSearchKey())
            .or().like(StoreUserExtinfoImportDO::getMobile,request.getSearchKey());
            lambdaQueryWrapper.and(searchKey);
        }
        if(Objects.isNull(request.getSort())){
            lambdaQueryWrapper.orderByDesc(StoreUserExtinfoImportDO::getCreateAt);
        }else{
            queryWrapper.orderBy(Objects.nonNull(request.getSort()),request.getSort().isAsc(),request.getSort().getColumns());
        }
        return baseMapper.selectPage(new Page<>(request.getPageNo(),request.getPageSize()),lambdaQueryWrapper);
    }

    public StoreUserExtinfoImportDO getByUserId(UserId userId){
        QueryWrapper<StoreUserExtinfoImportDO> queryWrapper=new QueryWrapper();
        LambdaQueryWrapper<StoreUserExtinfoImportDO> lambdaQueryWrapper =queryWrapper.lambda();
        lambdaQueryWrapper.eq(StoreUserExtinfoImportDO::getUserId,userId.getId())
        return baseMapper.selectList(lambdaQueryWrapper).stream().findFirst().orElse(null);
    }


}
