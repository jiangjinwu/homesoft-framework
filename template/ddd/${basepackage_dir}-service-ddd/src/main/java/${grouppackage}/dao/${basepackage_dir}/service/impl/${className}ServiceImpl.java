/*
 * 创 建 人: jjw
 * 创建时间: ${date}
 */

<#assign className=table.className>
    <#assign classNameLower=className?uncap_first>
    <#assign shortName=table.shortName>
package ${basepackage}.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${grouppackage}.dao.${basepackage}.service.${className}Service;
import ${grouppackage}.dao.${basepackage}.domain.${className}DO;


/**
 * @auther jjw
 */
@Service
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper,${className}DO>implements ${className}Service{

    @Override
    public IPage<${className}DO>search(${className}SearchRequest request){
        QueryWrapper<${className}DO> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<${className}DO>lambdaQueryWrapper=queryWrapper.lambda();
        if(StringUtils.isNotEmpty(request.getSearchKey())){
            Consumer<LambdaQueryWrapper<${className}DO>>searchKey=wrapper->wrapper.like(${className}DO::getName,request.getSearchKey())
            .or().like(${className}DO::getMobile,request.getSearchKey());
            lambdaQueryWrapper.and(searchKey);
        }
        if(Objects.isNull(request.getSort())){
            lambdaQueryWrapper.orderByDesc(${className}DO::getCreateAt);
        }else{
            queryWrapper.orderBy(Objects.nonNull(request.getSort()),request.getSort().isAsc(),request.getSort().getColumns());
        }
        return baseMapper.selectPage(new Page<>(request.getPageNo(),request.getPageSize()),lambdaQueryWrapper);
    }

    public ${className}DO getByUserId(UserId userId){
        QueryWrapper<${className}DO> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<${className}DO> lambdaQueryWrapper =queryWrapper.lambda();
        lambdaQueryWrapper.eq(${className}DO::getUserId,userId.getId())
        return baseMapper.selectList(lambdaQueryWrapper).stream().findFirst().orElse(null);
    }


}
