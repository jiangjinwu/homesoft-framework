<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${basepackage}.service;

import java.util.List;
import java.util.Map;


import  ${grouppackage}.${basepackage}.domain.${className}DO;

/**
 * @version 1.0
 * @author jjw
 */
public interface ${className}Service extends IService<${className}DO> {

        List<${className}> search(${className}SearchDTO search);

}
