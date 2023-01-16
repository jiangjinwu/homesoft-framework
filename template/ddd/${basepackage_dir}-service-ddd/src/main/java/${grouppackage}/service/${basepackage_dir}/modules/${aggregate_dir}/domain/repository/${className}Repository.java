<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.modules.${aggregate}.domain.repository;

public interface ${className}Repository {
    ${className}VO of${className}Id(${className}Id ${classNameLower}Id);

    Boolean remove${className}Cache(Long id);
}
