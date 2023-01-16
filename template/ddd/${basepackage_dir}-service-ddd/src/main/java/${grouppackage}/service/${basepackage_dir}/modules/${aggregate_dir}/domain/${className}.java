<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.modules.${aggregate}.domain;


public interface ${className} {
        ${className}DO data();
        ${className}Id getId();
}
