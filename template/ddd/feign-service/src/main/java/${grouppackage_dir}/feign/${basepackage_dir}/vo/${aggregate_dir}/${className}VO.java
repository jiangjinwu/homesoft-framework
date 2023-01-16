<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${aggregate}.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * ${table.tableAlias}
 * @version 1.0
 * @author jjw
 * @date {DATA}
 */
@Data
public class ${className}VO implements Serializable {

    <#list table.columns as column>
    // ${column.columnAlias}
     @ApiModelProperty("${column.columnAlias}")
    private ${column.simpleJavaType} ${column.columnNameLower};
    </#list>
}
