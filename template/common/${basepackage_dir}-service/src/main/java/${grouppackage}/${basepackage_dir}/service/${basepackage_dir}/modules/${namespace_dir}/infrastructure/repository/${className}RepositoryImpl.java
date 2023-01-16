<#include "/macro.include"/>
        <#include "/java_copyright.include">
        <#assign className = table.className>
        <#assign classNameLower = className?uncap_first>

package ${basepackage}.modules.${aggregate}.infrastructure.repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ${grouppackage}.service.${basepackage}.modules.utils.${classNameLower}Utils;

@Service
@Slf4j
@RequiredArgsConstructor
public class ${className}RepositoryImpl implements ${className}Repository {

    final ${className}Service ${classNameLower}Service;

    public ${className}VO of${className}Id(${className}Id ${classNameLower}Id){
        ${className}DO ${classNameLower}DO = ${classNameLower}Service.getById(${classNameLower}Id.getValue());
        return ${className}Utils.do2VO(${classNameLower}DO);
    }

    @Override
    public List<${className}VO> search(${className}SearchDTO searchDTO) {
        QueryWrapper<${className}DO> queryWrapper = new QueryWrapper();
        LambdaQueryWrapper<${className}DO> lambdaQueryWrapper =queryWrapper.lambda();
        lambdaQueryWrapper.eq(Objects.nonNull(searchDTO.getId()),${className}DO::getId, searchDTO.getId());

        if (Objects.isNull(searchDTO.getSort())) {
            //lambdaQueryWrapper.orderByDesc(${classNameLower}DO::getJoinTime);
        } else {
            queryWrapper.orderBy(Objects.nonNull(searchDTO.getSort()), searchDTO.getSort().isAsc(), FieldColumnUtils.convert(searchDTO.getSort().getColumns()));
        }

        List<${className}DO> doList = ${classNameLower}Service.list(lambdaQueryWrapper);
        return ${className}Utils.do2VOList(doList);
        }
}
