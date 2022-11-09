<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.${namespace}.model;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * ${table.tableAlias}
 * @version 1.0
 * @author jjw
 */
@Data
public class ${className}SearchDTO extends ${className}SearchRequest  implements Serializable {

    private String searchKey;
<#list table.columns as column>
    <#if  column.simpleJavaType  == 'LocalDateTime'>
        @ApiModelProperty("${column.columnAlias}开始")
        private ${column.simpleJavaType} ${column.columnNameLower}Start;

        @ApiModelProperty("${column.columnAlias}结束")
        private ${column.simpleJavaType} ${column.columnNameLower}End;
    <#elseif !column.pk>
        @ApiModelProperty("${column.columnAlias}")
        private ${column.simpleJavaType} ${column.columnNameLower};
    </#if>

</#list>
    Sort sort;
@NotNull(message = "pageSize不能为空")
    Integer pageSize;
@NotNull(message = "pagepageNo不能为空")
    Integer pageNo;


}
//<#macro generateJavaColumns>
//    <#list table.columns as column>
//    <@generateBycondition column.sqlName>
//    public void set${column.columnName}(${column.simpleJavaType} value) {
//        this.${column.columnNameLower} = value;
//    }
//
//    public ${column.simpleJavaType} get${column.columnName}() {
//        return this.${column.columnNameLower};
//    }
//
//    </@generateBycondition>
//    </#list>
//</#macro>
