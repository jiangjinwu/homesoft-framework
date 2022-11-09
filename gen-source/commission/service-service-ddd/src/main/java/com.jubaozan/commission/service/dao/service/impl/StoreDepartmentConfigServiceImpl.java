/*
 * 文 件 名:  StoreDepartmentConfigServiceImpl.java
 * 创 建 人:
 * 创建时间:
 */

package commission.service.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubaozan.dao.commission.service.service.StoreDepartmentConfigService;
import com.jubaozan.dao.commission.service.domain.StoreDepartmentConfigDO;


/**
 * @auther jjw
 */
@Service
public class StoreDepartmentConfigServiceImpl extends ServiceImpl<StoreDepartmentConfigMapper,StoreDepartmentConfigDO>implements StoreDepartmentConfigService{

    @Override
    public IPage<StoreDepartmentConfigDO>search(StoreDepartmentConfigSearchRequest request){
        QueryWrapper<StoreDepartmentConfigDO> queryWrapper=new QueryWrapper();
        LambdaQueryWrapper<StoreDepartmentConfigDO>lambdaQueryWrapper=queryWrapper.lambda();
        if(StringUtils.isNotEmpty(request.getSearchKey())){
            Consumer<LambdaQueryWrapper<StoreDepartmentConfigDO>>searchKey=wrapper->wrapper.like(StoreDepartmentConfigDO::getName,request.getSearchKey())
            .or().like(StoreDepartmentConfigDO::getMobile,request.getSearchKey());
            lambdaQueryWrapper.and(searchKey);
        }
        if(Objects.isNull(request.getSort())){
            lambdaQueryWrapper.orderByDesc(StoreDepartmentConfigDO::getCreateAt);
        }else{
            queryWrapper.orderBy(Objects.nonNull(request.getSort()),request.getSort().isAsc(),request.getSort().getColumns());
        }
        return baseMapper.selectPage(new Page<>(request.getPageNo(),request.getPageSize()),lambdaQueryWrapper);
    }

    public StoreDepartmentConfigDO getByUserId(UserId userId){
        QueryWrapper<StoreDepartmentConfigDO> queryWrapper=new QueryWrapper();
        LambdaQueryWrapper<StoreDepartmentConfigDO> lambdaQueryWrapper =queryWrapper.lambda();
        lambdaQueryWrapper.eq(StoreDepartmentConfigDO::getUserId,userId.getId())
        return baseMapper.selectList(lambdaQueryWrapper).stream().findFirst().orElse(null);
    }


}
