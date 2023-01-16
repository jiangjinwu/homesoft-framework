<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.modules.${aggregate}.domain.impl;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BusinessDistrictMember
 * @version 1.0
 * @author jjw
 */

@Slf4j
@RequiredArgsConstructor
public class ${className}Impl implements ${className} {
    private final ${className}DO data;
    private final ${className}Repository repository;

    public ${className}DO data(){

        return this.data;
    }
     public ${className}Id getId(){
        return new ${className}Id(data.getId());
     }
}
