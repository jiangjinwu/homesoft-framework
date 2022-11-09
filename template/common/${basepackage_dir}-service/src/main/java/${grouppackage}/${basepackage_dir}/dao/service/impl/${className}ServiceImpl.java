/*
 * 文 件 名:  ${table.className}ServiceImpl.java
 * 创 建 人:  
 * 创建时间:  
 */
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ${grouppackage}.dao.${basepackage}.service.${className}Service;
import ${grouppackage}.dao.${basepackage}.domain.${className}DO;


/**
 * @auther jjw
 */
@Service
public class ${className}ServiceImpl  extends ServiceImpl<${className}Mapper, ${className}DO>  implements ${className}Service {

   @Override
   public List<${className}DO> search(${className}SearchRequest request) {
        LambdaQueryWrapper<${className}DO> lambdaQueryWrapper = new LambdaQueryWrapper();
        return baseMapper.selectList(lambdaQueryWrapper);
   }

    public List<${className}DO> listPage(Map<String, Object> params){
        List<${className}> lists = baseMapper.listPage(params);

        return lists;
    }
}
