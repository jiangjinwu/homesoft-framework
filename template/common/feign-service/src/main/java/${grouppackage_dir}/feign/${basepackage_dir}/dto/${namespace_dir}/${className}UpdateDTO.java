<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${namespace}.model;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
/**
 * ${table.tableAlias}
 * @version 1.0
 * @author
 */
@Data
public class ${className}UpdateDTO implements Serializable {
<#list table.columns as column>
   <#if  column.simpleJavaType  != 'LocalDateTime'>
   @ApiModelProperty("${column.columnAlias}")
    private ${column.simpleJavaType} ${column.columnNameLower};
   </#if>

</#list>
}
