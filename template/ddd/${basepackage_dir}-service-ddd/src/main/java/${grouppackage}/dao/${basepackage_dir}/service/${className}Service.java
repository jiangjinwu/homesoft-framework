<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.service;

import java.util.List;
import java.util.Map;


import  ${grouppackage}.dao.${basepackage}.domain.${className}DO;

/**
 * @version 1.0
 * @author jjw
 */
public interface ${className}Service extends IService<${className}DO> {

     IPage<${className}DO> search(${className}SearchDTO search);

     ${className}DO getByUserId(UserId userId);

}
