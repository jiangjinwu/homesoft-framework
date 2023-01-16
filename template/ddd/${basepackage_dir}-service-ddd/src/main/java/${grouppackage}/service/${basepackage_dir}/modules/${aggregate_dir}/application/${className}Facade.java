<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${grouppackage}.service.${basepackage}.modules.application;

@Component
public class ${className}Facade{
    @Autowired
    private ${className}Service ${classNameLower}Service;

    public Boolean create(String tenantId,UserId userId,${className}CreationRequest request){
        ${className}Service ${classNameLower}DO = ${className}Utils.creation2VO(request);
        ${classNameLower}DO.setUserId(userId.getId());
        ${classNameLower}DO.setTenantId(tenantId);
        ${classNameLower}Service.save(${classNameLower}DO);
        return true;
    }

}
