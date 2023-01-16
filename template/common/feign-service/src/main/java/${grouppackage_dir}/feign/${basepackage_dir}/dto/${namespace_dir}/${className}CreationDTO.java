<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${aggregate}.model;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Max;
/**
 * ${table.tableAlias}
 * @version 1.0
 * @author
 */
@Data
public class ${className}CreationDTO implements Serializable {
<#list table.columns as column>
 <#if column.nullable == false><#if column.javaType == 'java.lang.String'>
    @NotEmpty(message="${column.columnAlias}不能为空")@Max(value = ${column.size},  message = "最大长度为${column.size}")<#else>        @NotNull</#if></#if>
   <#if  column.simpleJavaType  != 'LocalDateTime'>
    @ApiModelProperty("${column.columnAlias}")
    private ${column.simpleJavaType} ${column.columnNameLower};
   </#if>

</#list>
}
